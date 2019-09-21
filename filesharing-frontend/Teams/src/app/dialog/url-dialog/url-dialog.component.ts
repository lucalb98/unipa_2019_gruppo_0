import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {ActivatedRoute} from "@angular/router";
import {UrlDTO} from "../../models/UrlDTO";
import {UrlService} from "../../services/url.service";

@Component({
  selector: 'app-url-dialog',
  templateUrl: './url-dialog.component.html',
  styleUrls: ['./url-dialog.component.scss']
})
export class UrlDialogComponent implements OnInit {
  urlstring:string;
  constructor( public dialogRef: MatDialogRef<UrlDialogComponent>,
               @Inject(MAT_DIALOG_DATA) public url: UrlDTO) {
  }

  ngOnInit(){
  this.urlstring=this.url.url;
  }


  copyMessage(val: string){
      let selBox = document.createElement('textarea');
      selBox.style.position = 'fixed';
      selBox.style.left = '0';
      selBox.style.top = '0';
      selBox.style.opacity = '0';
      selBox.value = val;
      document.body.appendChild(selBox);
      selBox.focus();
      selBox.select();
      document.execCommand('copy');
      document.body.removeChild(selBox);
    }


}
