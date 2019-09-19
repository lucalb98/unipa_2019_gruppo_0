import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-url-dialog',
  templateUrl: './url-dialog.component.html',
  styleUrls: ['./url-dialog.component.scss']
})
export class UrlDialogComponent implements OnInit {
  public url:string;

  constructor( public dialogRef: MatDialogRef<UrlDialogComponent>,
               @Inject(MAT_DIALOG_DATA) public data: any) {
  }

  ngOnInit() {
  }

  copiaUrl(){
      document.querySelector("#pulsante")=function() {
      document.querySelector("testo");
      document.execCommand('copy');
    }

    this.dialogRef.close(this.url);
  }

}
