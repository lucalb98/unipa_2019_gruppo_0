import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {OtpService} from "../../services/otp.service";
import {UrlService} from "../../services/url.service";

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

  constructor(private urlService: UrlService,
      private router: ActivatedRoute) { }

  ngOnInit() {
  }


  public downloadFile(){
    this.urlService.download({uuid:this.uuid,uniqueId:this.uniqueId,token:this.token,bucketName:this.bucketName});
  }

}
