import {EventEmitter, Injectable, OnInit} from '@angular/core';
import {Observable} from "rxjs";
import {ActivatedRoute, NavigationEnd, Router} from "@angular/router";


export enum SYNC_TYPE {
  Team,
  Bucket,
  Resource,
  Email =3
}

@Injectable({
  providedIn: 'root'
})
export class SyncService implements OnInit{

  private iteration: number = 0;
  private registry: EventEmitter<SYNC_TYPE> = new EventEmitter();

  constructor(private route: Router) {
    // this.route.events.subscribe((data)=>{
    //   if(data instanceof NavigationEnd){
    //     let temp: NavigationEnd = data;
    //     console.log(route.parseUrl(temp.url), temp.url, temp);
    //   }
    //   console.log("Sync -> ", data);
    // })
  }

  ngOnInit(): void {

  }


  register(){
    return this.registry.asObservable();
  }
  
  sendEvent(type: SYNC_TYPE){
    this.registry.emit(type);
  }
}
