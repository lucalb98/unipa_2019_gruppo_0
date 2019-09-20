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
  constructor( public dialogRef: MatDialogRef<UrlDialogComponent>,
               @Inject(MAT_DIALOG_DATA) public url: UrlDTO) {
  }

  ngOnInit(){

  }

  copiaUrl(){

      //document.querySelector("#pulsante")=function() {
      //document.querySelector("testo");
      //document.execCommand('copy');
      //}

   // this.dialogRef.close(this.url);
  }

}
