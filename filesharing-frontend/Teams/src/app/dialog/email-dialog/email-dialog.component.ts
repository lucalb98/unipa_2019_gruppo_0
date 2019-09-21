import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {MatSnackBar} from "@angular/material/snack-bar";
import {SnackBarComponent} from "../snack-bar/snack-bar.component";

@Component({
  selector: 'app-email-dialog',
  templateUrl: './email-dialog.component.html',
  styleUrls: ['./email-dialog.component.scss']
})
export class EmailDialogComponent implements OnInit {

  public email: string;


    constructor( public dialogRef: MatDialogRef<EmailDialogComponent>,
                 public _snackBar:MatSnackBar,
                 @Inject(MAT_DIALOG_DATA) public data: any) {
    }

  ngOnInit() {
  }

  addEmail(){
    this.dialogRef.close(this.email);
    this._snackBar.open('Email inviata con successo!', '',{
      duration:3000
    });

  }


}
