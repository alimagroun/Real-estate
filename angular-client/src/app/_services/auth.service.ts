import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams  } from '@angular/common/http';
import { Observable } from 'rxjs';
import {User} from '../_models/user';

const AUTH_API = 'http://localhost:8080/api/auth/';


const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root',
})
export class AuthService {

  private baseUrl = 'http://localhost:8080/api/';

  constructor(private http: HttpClient) {}

  checkResetCode(email: string, resetCode: string) {
    const credentials = { email,resetCode };
    return this.http.post(`${this.baseUrl}checkResetCode`, credentials);
  }

//`${this.baseUrl1}/last4`
  initiatePasswordReset(email: string) {
    const headers = new HttpHeaders({ 'Content-Type': 'application/x-www-form-urlencoded' });
    const body = new URLSearchParams();
    body.set('email', email);

    return this.http.post<boolean>(`${this.baseUrl}send-reset-code`, body.toString(), { headers });
  }

  isLoggedIn(): Observable<boolean> {
    return this.http.get<boolean>(`${AUTH_API}check-auth`);
  }

  getAll(): Observable<User[]>{
    return this.http.get<User[]>(`${AUTH_API}users`);
  }
  

  login(username: string, password: string): Observable<any> {
    return this.http.post(
      AUTH_API + 'signin',
      {
        username,
        password,
      },
      httpOptions
    );
  }

  register(username: string, email: string, password: string): Observable<any> {
    return this.http.post(
      AUTH_API + 'signup',
      {
        username,
        email,
        password,
      },
      httpOptions
    );
  }

  logout(): Observable<any> {
    return this.http.post(AUTH_API + 'signout', { }, httpOptions);
  }
}