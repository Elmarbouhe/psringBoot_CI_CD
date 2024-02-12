import { Customer } from './../model/customer.model';
import { environment } from './../../environment/environment';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  constructor(private http:HttpClient) { }

  public getAllCustomers():Observable<Array<Customer>>{
    return this.http.get<Array<Customer>>(environment.api+"customers/list").pipe(
      catchError(this.handleError)
    );
  }

  public searchCustomer(name : String):Observable<Array<Customer>>{
    return this.http.get<Array<Customer>>(environment.api+"customers/searchByName?name="+name)
  }

  searchCustomerByEmail(email: String):Observable<Array<Customer>>{
    return this.http.get<Array<Customer>>(environment.api+"customers/searchByEmail?email="+email)
  }

  addnewCustomer(customer: Customer):Observable<Customer>{
    return this.http.post<Customer>(environment.api+"customers/create",customer)
  }

  deleteCustomer(id: number){
    return this.http.delete(environment.api+"customers/delete/"+id)
  }


  private handleError(error: HttpErrorResponse) {
    if (error.status === 0) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error);
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong.
      console.error(
        `Backend returned code ${error.status}, body was: `, error.error);
    }
    // Return an observable with a user-facing error message.
    return throwError(() => new Error('Something bad happened; please try again later.'));
  }



}
