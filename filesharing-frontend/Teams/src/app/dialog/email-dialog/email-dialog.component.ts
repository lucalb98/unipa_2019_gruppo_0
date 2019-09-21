import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {MatSnackBar} from "@angular/material/snack-bar";
import {SnackBarComponent} from "../snack-bar/snack-bar.component";
import {FormControl, FormGroupDirective, NgForm, Validators} from "@angular/forms";
import {ErrorStateMatcher} from "@angular/material/core";


export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}

@Component({
  selector: 'app-email-dialog',
  templateUrl: './email-dialog.component.html',
  styleUrls: ['./email-dialog.component.scss']
})

export class InputErrorStateMatcherExample implements OnInit{
  emailFormControl = new FormControl('', [
    Validators.required,
    Validators.email,
  ]);




  matcher = new MyErrorStateMatcher();
  public email: string;


    constructor( public dialogRef: MatDialogRef<InputErrorStateMatcherExample>,
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
