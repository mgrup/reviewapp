import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import {RegisterUser} from '../model/RegisterUser';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class LoginService {
  private registerUrl:string = "rest/api/user/registration";

  constructor(private http: HttpClient) { }

  registerUser(user: RegisterUser): Observable<RegisterUser>{
  	return this.http.post<RegisterUser>(this.registerUrl, user, httpOptions)
  		.pipe(catchError(this.handleError<RegisterUser>()));
  }

  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.log(error);
      // Let the app keep running by returning an empty result.
      return Observable.throw(error);
    };
  }

}
