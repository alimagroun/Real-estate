import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PropertyService {

  private baseUrl = 'http://localhost:8080/api/properties';
  
  constructor(private http: HttpClient) { }

  getPropertyList(): Observable<any> {
    return this.http.get(`${this.baseUrl}`);
  }

}
