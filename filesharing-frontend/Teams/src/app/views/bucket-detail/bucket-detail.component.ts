import {Component, OnInit} from '@angular/core';
import {BucketService} from "../../services/bucket.service";
import {ActivatedRoute, UrlSegment} from "@angular/router";
import {ResourceService} from "../../services/resource.service";
import {SYNC_TYPE, SyncService} from "../../services/sync.service";
import {TeamService} from "../../services/team.service";
import {UploadProgressModel} from "../../models/UploadProgressModel";
import {HttpEventType} from "@angular/common/http";
import {TeamDialogComponent} from "../../dialog/team-dialog/team-dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {EmailDialogComponent} from "../../dialog/email-dialog/email-dialog.component";
import {EmailService} from "../../services/email.service";
import {EmailDTO} from "../../models/EmailDTO";
import {ResourceDTO, TeamDTO} from "../../models/models";
import {environment} from "../../../environments/environment";

//import {Component, Inject, OnInit} from '@angular/core';
class PathDescriptor{
  path: string;
  uniqueId: string;
}

@Component({
  selector: 'app-bucket-detail',
  templateUrl: './bucket-detail.component.html',
  styleUrls: ['./bucket-detail.component.scss']
})
export class BucketDetailComponent implements OnInit {

  public showMenu: boolean = false;
  public files: ResourceDTO;
  private originalResources: ResourceDTO;
  public team: string;
  public bucket: string;
  public teamDTO: TeamDTO;
  public urlparams: UrlSegment[] = [];
  public folderList: PathDescriptor[];

  constructor(public emailService: EmailService,
              public dialog: MatDialog,
              private teamService: TeamService,
              private bucketService: BucketService,
              private resourceService: ResourceService,
              private syncService: SyncService,
              private router: ActivatedRoute) {
  }

  ngOnInit() {
    this.router.paramMap.subscribe(params => {
      console.log("ROUTE PARAMS");
      this.team = params.get('team');
      this.bucket = params.get('bucket');
      this.loadResource();
      this.syncService.register().subscribe((type: SYNC_TYPE) => {
        console.log("Sync", type)
        if (type == SYNC_TYPE.Resource) {
          this.loadResource();
        }
      });
    });
    this.router.url.subscribe((data) => {
      console.log('params ', data); //contains all the segments so put logic here of determining what to do according to nesting depth
      this.urlparams = data;
      this.navigateFolder(this.originalResources);
    });

  }

  loadResource() {
    this.teamDTO = this.teamService.getTeamByUUID(this.team);
    this.resourceService.get(this.team, this.bucket).subscribe(data => {
      this.originalResources = data;
      this.navigateFolder(data);
    });
  }

  private navigateFolder(data: ResourceDTO) {
    this.folderList = [];
    if (this.urlparams && this.urlparams.length > 0 && data) {
      let find = data;
      for (let folder of this.urlparams) {
        find = find.childs.find(x => {
          return x.uniqueKey == folder.path
        });
        if (find) {
          this.folderList.push({path: find.name, uniqueId: folder.path});
        } else {
          console.log("non ho trovato la folder " + folder, find);
        }
      }
      this.files = find;
    } else {
      this.files = data;
    }
  }

  deleteAttachment(index) {
  }

  uploadFileEvent(file: File) {
    this.resourceService.addContent(this.team, this.bucket, this.urlparams.length > 0 ? this.urlparams[this.urlparams.length - 1].path : null, file).subscribe((data: UploadProgressModel) => {
      console.log(data);
      if (data.status == HttpEventType.Response.toString()) {
        this.syncService.sendEvent(SYNC_TYPE.Resource);
      } else {
        //upload progress
      }
    });
  }

  download(file: ResourceDTO) {
    this.resourceService.download(this.team, this.bucket, file.uniqueKey);
  }

  getPathForLink(index) {
    return this.urlparams.slice(0, index + 1).reduce((initial, item) => {
      initial.push(item.path);
      return initial
    }, []).join('/');
  }

  LinkDownload(file) {
    console.log(environment.sharedUrl+'')
  }

  openDialogEmail(uniqueId: string):void {
    const dialogRef = this.dialog.open(EmailDialogComponent, {
      width:"50vw",
      data:{}
    });
    this.showMenu = false;
    dialogRef.afterClosed().subscribe((result: string) => {
      if (result) {
        // result.email.push({email: "Default"});
        let emailDTO: EmailDTO = {email: result, bucketName: this.bucket, uuid: this.team, uniqueId: uniqueId};
        this.emailService.sendEmail(emailDTO).subscribe(data => {
          this.syncService.sendEvent(SYNC_TYPE.Email);
          console.log("E-mail inviata con successo!");
        });
      }
    })
  }
}

    //PROVA!!!!!!!!!!!!!
  /* const dialogRef = this.dialog.open(TeamDialogComponent, {
      width: '500vw',
      data: {}
    });
    this.showMenu = false;
    dialogRef.afterClosed().subscribe((result: TeamDTO) => {
      if (result) {
        result.buckets = [];
        result.buckets.push({name: 'Default', bucketType: "BUCKET", description: 'default'});
        // this.teamService.addTeam(result);
        this.teamService.save(result).subscribe(data => {
          this.syncService.sendEvent(SYNC_TYPE.Team);
        });

      }
    });}
  }*/
