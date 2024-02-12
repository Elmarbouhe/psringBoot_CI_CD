import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { CustomerService } from '../services/customer.service';
import { Observable, catchError } from 'rxjs';
import { Customer } from '../model/customer.model';
import { FormBuilder, FormGroup } from '@angular/forms';
import { NotExpr } from '@angular/compiler';
import { Router } from '@angular/router';

@Component({
  selector: 'app-customers',
  templateUrl: './customers.component.html',
  styleUrls: ['./customers.component.css']
})
export class CustomersComponent implements OnInit {

  constructor(private CustomerService:CustomerService, private fb:FormBuilder, private router:Router) { }

  customers! :  Observable<Array<Customer>>;
  errorMessage! : object;
  searchFormGroup : FormGroup | undefined;

  ngOnInit(): void {

    this.searchFormGroup = this.fb.group({
      name : this.fb.control(""),
      email :this.fb.control("")
    });
    this.handleSearch();

    }

    handelSearchCustomersBayName(){
      let name = this.searchFormGroup?.value.name;
      this.customers = this.CustomerService.searchCustomer(name).pipe(
      catchError(err => {
        this.errorMessage = err; return [];
      } ));
    }

    handelSearchCustomersBayEmail(){
      let email = this.searchFormGroup?.value.email;
      this.customers = this.CustomerService.searchCustomerByEmail(email).pipe(
      catchError(err => {
        this.errorMessage = err; return [];
      } ));
    }

    handleSearch(){
      let name = this.searchFormGroup?.value.name;
      let email = this.searchFormGroup?.value.email;
      if(name){
        this.handelSearchCustomersBayName();
      }else if(email){
        this.handelSearchCustomersBayEmail();
      }else{
        this.customers = this.CustomerService.getAllCustomers().pipe(
          catchError(err => {
            this.errorMessage = err; return [];
          } ));
      }
    }

    handleDelete(customer: Customer){
      let confirm = window.confirm("Are you sure you want to delete this customer?");
      if(!confirm){
        return;
      }
      this.CustomerService.deleteCustomer(customer.id).subscribe(
        (resp) => {
        this.customers = this.CustomerService.getAllCustomers().pipe(
          catchError(err => {
            this.errorMessage = err; return [];
          } ));
        },
        (err) => {
          this.errorMessage = err;
        }
      )
    }

    handleCustomerAccount(Customer: Customer){
      this.router.navigate(['/customer-accounts', Customer.id ] , {state: {customer: Customer}});
    }


}
