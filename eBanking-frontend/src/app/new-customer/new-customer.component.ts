import { CustomerService } from './../services/customer.service';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Customer } from '../model/customer.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-new-customer',
  templateUrl: './new-customer.component.html',
  styleUrls: ['./new-customer.component.css']
})
export class NewCustomerComponent implements OnInit {

  constructor(private formBuilder:FormBuilder, private customerService:CustomerService, private router:Router) { }

  addCustomerFormGroup : FormGroup | undefined;



  ngOnInit() {
    this.addCustomerFormGroup = this.formBuilder.group({
      name  :  this.formBuilder.control(null, [
        Validators.required,
        Validators.minLength(3),
      ]),
      email :  this.formBuilder.control(null,[
        Validators.required,
        Validators.email,
      ])
    })

  }

  handleAddCustomer(){
    let customer:Customer = this.addCustomerFormGroup?.value
    this.customerService.addnewCustomer(customer).subscribe({
      next : (response) => {
        alert("Customer Added Successfully")
        // this.addCustomerFormGroup?.reset()
        this.router.navigate(['/customers'])
      },
      error : (error) => {
        console.log(error)
      }
    })
  }

}
