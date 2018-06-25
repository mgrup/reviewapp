import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import {User} from '../model/User';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class LoginService {
  private registerUrl:string = "rest/api/user/registration";
  private loginUrl:string = "http://localhost:8080/login";
  private getUserUrl:string = "http://localhost:8080/user"
  private authenticated:boolean;

  constructor(private http: HttpClient) {
    http.get('/rest/api/token').subscribe(data => {
      const token = data['token'];
      console.log(token)
      // http.get('http://localhost:8080', {headers : new HttpHeaders().set('X-Auth-Token', token)})
      //   .subscribe(response => {console.log("in response from token")});
    }, () => {});
  }

  registerUser(user: User): Observable<User>{
  	return this.http.post<User>(this.registerUrl, user, httpOptions)
  		.pipe(catchError(this.handleError<User>()));
  }

  login(user: User): Observable<User>{
    console.log(user);
    user.username = user.email;
    let body = new URLSearchParams();
    body.set('username', user.username);
    body.set('password', user.password);

    return this.http.post(this.loginUrl, /*user*/body.toString(), {headers: new HttpHeaders({"Content-Type":"application/x-www-form-urlencoded"})})
      .pipe(catchError(this.handleError<User>()));
  }

  authenticate(credentials, callback) {
    console.log(credentials)
    const headers = new HttpHeaders(credentials ? {
        authorization : 'Basic ' + btoa(credentials.username + ':' + credentials.password)
    } : {});
  }
  getUser(){
    this.http.get(this.getUserUrl).subscribe(response => {
        if (response && response['name']) {
            this.authenticated = true;
        } else {
            this.authenticated = false;
        }
        return callback && callback();
    });
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
