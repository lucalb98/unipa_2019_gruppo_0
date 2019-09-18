import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";

@Component({
  selector: 'app-email-dialog',
  templateUrl: './email-dialog.component.html',
  styleUrls: ['./email-dialog.component.scss']
})
export class EmailDialogComponent implements OnInit {

  public email: string;

    constructor( public dialogRef: MatDialogRef<EmailDialogComponent>,
                 @Inject(MAT_DIALOG_DATA) public data: any) {
    }

  ngOnInit() {
  }

  addEmail(){
    this.dialogRef.close(this.email);
  }


}
