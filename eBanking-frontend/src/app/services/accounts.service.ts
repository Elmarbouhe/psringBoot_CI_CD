import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { environment } from 'src/environment/environment';
import { AccountOperation, accountDetails } from '../model/account.model';

@Injectable({
  providedIn: 'root'
})
export class AccountsService {

  constructor(private http:HttpClient) { }

  getAccount(accountId:String, pages:number, size:number):Observable<accountDetails>{
    return this.http.get<accountDetails>(environment.api+'operations/accountHistory/'+accountId+'?page='+pages+'&size='+size)
  }

  public debit(accountId:String, amount:number, description:String){
    return this.http.post(environment.api+'operations/debit',{accountId:accountId,amount:amount,description:description})
  }

  public credit(accountId:String, amount:number, description:String){
    return this.http.post(environment.api+'operations/credit',{accountId:accountId,amount:amount,description:description})
  }

  public transfer(accountSource:String, accountDestination:String, amount:number, description:String){
    return this.http.post(environment.api+'operations/transfer',{accountSource,accountDestination,amount,description})
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
