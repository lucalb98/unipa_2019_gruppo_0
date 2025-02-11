import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {OtpDTO} from "../models/OtpDTO";
import {share} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class OtpService {

  private baseUrl: string = environment.apiBaseUrl+"/otp";

  constructor(private httpClient:HttpClient) { }

  public sendOtp(otpDTO: OtpDTO){
    return this.httpClient.post(this.baseUrl+'/sendOtp', otpDTO);
  }

  public download(otpDTO: OtpDTO){
    let reg = this.httpClient.post(this.baseUrl+'/download', otpDTO, {responseType: 'arraybuffer', observe: 'response'}).pipe(share());
    reg.subscribe(res => {
      return this.downLoadFile(res);
    });
    return reg;
  }

  private downLoadFile(data: any) {
    var blob = new Blob([data.body], {type: data.headers.get('content-type')});
    const element = document.createElement('a');
    element.href = URL.createObjectURL(blob);
    const contentDispositionHeader: string = data.headers.get('content-disposition'); // <== Getting error here, Not able to read Response Headers
    const parts: string[] = contentDispositionHeader.split(';');
    const filename = parts[1].split('=')[1].replace(/\"/g, "");
    element.download = filename;
    // element.target = "_blank";
    let elem = document.body.appendChild(element);
    element.click();
    console.log(elem, element);
    document.body.removeChild(elem);
  }
}
