import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {UrlDTO} from "../models/UrlDTO";
import {share} from "rxjs/operators";
import {Observable} from "rxjs";
import {MatSnackBar} from "@angular/material/snack-bar";



@Injectable({
  providedIn: 'root'
})
export class UrlService {
  private baseUrl: string = environment.apiBaseUrl + "/url";


  constructor(private httpClient: HttpClient,
              private _snackBar: MatSnackBar) {
  }

  public generateUrl(urlDTO:UrlDTO):Observable<UrlDTO> {
    return this.httpClient.post<UrlDTO>(this.baseUrl, urlDTO);
  }

  public download(urlDTO: UrlDTO) {
    let reg = this.httpClient.post(this.baseUrl+'/download', urlDTO, {responseType: 'arraybuffer', observe: 'response'}).pipe(share());
    reg.subscribe(res => {
     this._snackBar.open("Download in corso...","",{
        duration: 3000
      });
      return this.downLoadFile(res);
    },error => {
      this._snackBar.open("URL scaduto o errato!","",{
        duration:3000
      });
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