import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {OtpService} from "../../services/otp.service";
import {UrlService} from "../../services/url.service";
import {printLine} from "tslint/lib/verify/lines";

@Component({
  selector: 'app-url-detail',
  templateUrl: './url-detail.component.html',
  styleUrls: ['./url-detail.component.scss']
})
export class UrlDetailComponent implements OnInit {

  public uuid: string;
  public uniqueId: string;
  private token: string;
  public bucketName: string;
  public url:string;

  constructor(private urlService: UrlService,
      private router: ActivatedRoute) { }

  ngOnInit() {
    this.router.paramMap.subscribe(params => {
      console.log("ROUTE PARAMS");
      this.token = params.get('token');
    });
  }


  public downloadFile(){
    console.log("sono download file del frontend");
    console.log(this.uuid,this.token);
    this.urlService.download({uuid:this.uuid,uniqueId:this.uniqueId,token:this.token,bucketName:this.bucketName,url:this.url});
  }

}
