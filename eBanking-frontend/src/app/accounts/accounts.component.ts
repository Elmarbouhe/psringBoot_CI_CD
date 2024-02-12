import { AccountsService } from './../services/accounts.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Component } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { accountDetails } from '../model/account.model';

@Component({
  selector: 'app-accounts',
  templateUrl: './accounts.component.html',
  styleUrls: ['./accounts.component.css']
})
export class AccountsComponent {

  accountFormGroup! : FormGroup ;
  currentPage = 0 ;
  sizePage = 5;
  accountObservable! : Observable<accountDetails>
  operationFormGroup! : FormGroup ;
  errorMessage! : object;

  constructor(private formBuilder: FormBuilder,private accountsService:AccountsService) {}

  ngOnInit() {

    this.accountFormGroup = this.formBuilder.group({
      accountId: [''],
    });

    this.operationFormGroup = this.formBuilder.group({
      operationType: [null],
      amount: [0, [Validators.required, Validators.min(1)]],
      description: [null],
      accountDestination: [null]
    });

  }

  handelSerchAccount() {
    let accountId:String = this.accountFormGroup.value.accountId ;
    this.accountObservable = this.accountsService.getAccount(accountId,this.currentPage,this.sizePage).pipe(
      catchError(err => {
        this.errorMessage = err; 
        return [];
      } ));
    }


  goToPage(page:number) {
    this.currentPage = page ;
    this.handelSerchAccount();
  };

  handelAccountOperation(){
    let accountId:String = this.accountFormGroup.value.accountId ;
    let type:String = this.operationFormGroup.value.operationType;
    let amount:number = this.operationFormGroup.value.amount ;
    let description:String = this.operationFormGroup.value.description ;
    let accountDestination:String = this.operationFormGroup.value.accountDestination ;
   if(type == "DEBIT") {
    this.accountsService.debit(accountId,amount,description).subscribe(
      data => {
        alert("Success DEBIT");
        this.operationFormGroup.reset();
        this.handelSerchAccount();
      },err => {
        this.errorMessage = err; return [];
      });
   }else if(type == "CREDIT"){
    this.accountsService.credit(accountId, amount, description).subscribe(
      data => {
        alert("Success CREDIT");
        this.operationFormGroup.reset();
        this.handelSerchAccount();
      },err => {
        this.errorMessage = err; return [];
      });
   }else if(type == "TRANSACTION") {
    this.accountsService.transfer(accountId, accountDestination, amount, description).subscribe(
      data => {
        alert("Success TRANSACTION");
        this.operationFormGroup.reset();
        this.handelSerchAccount();
      },err => {
        this.errorMessage = err; return [];
      });
  } else {
     alert("Error Type Operation");
    }

  };



}
