import { Component, OnInit } from '@angular/core';
import {OtpService} from "../../services/otp.service";
import {SYNC_TYPE} from "../../services/sync.service";
import {ActivatedRoute} from "@angular/router";
import {BucketDTO, FolderDTO} from "../../models/models";

@Component({
  selector: 'app-otp-detail',
  templateUrl: './otp-detail.component.html',
  styleUrls: ['./otp-detail.component.scss']
})
export class OtpDetailComponent implements OnInit {
  private token: string;
  public email: string;
  public otp: number;

  constructor(private otpService: OtpService,
              private router: ActivatedRoute) {
  }

  ngOnInit() {
    this.router.paramMap.subscribe(params => {
      console.log("ROUTE PARAMS");
      this.token = params.get('token');
      this.email = params.get('email');
      this.otpService.sendOtp({email: this.email, token: this.token, otp: null}).subscribe(data => {
        console.log("Inviata");
      }, (error => {
        console.log(error);
      }))
    });
  }

  verificaOTP() {

    this.otpService.download({email: this.email, token: this.token, otp: this.otp});
  }

  rinvioOTP() {
    this.router.paramMap.subscribe(params => {
      console.log("ROUTE PARAMS");
      this.token = params.get('token');
      this.email = params.get('email');
      this.otpService.sendOtp({email: this.email, token: this.token, otp: null}).subscribe(data => {
        console.log("Inviata");
      }, (error => {
        console.log(error);
      }))
    });

  }
}