import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private baseUrl = 'http://localhost:8080/springboot-crud-rest/api/users';
  constructor(private http: HttpClient) { }

  getUsersList(): Observable<any> {
    return this.http.get(`${this.baseUrl}`);
  }



}
