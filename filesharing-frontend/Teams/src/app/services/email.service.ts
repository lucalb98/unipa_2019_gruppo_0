import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Observable, Subscription} from "rxjs";
import {EmailDTO} from "../models/EmailDTO";


@Injectable({
  providedIn: 'root'
})
export class EmailService {

  private baseUrl: string = environment.apiBaseUrl+"/shared";

  constructor(private httpClient:HttpClient) {}

  public sendEmail(email: EmailDTO){
    return this.httpClient.post(this.baseUrl, email);
  }
}
