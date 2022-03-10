import { Injectable } from '@angular/core';
import { Quote } from './quote';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map, Observable } from 'rxjs';


@Injectable()
export class QuoteService {
  private apiUrl = '/api/quotes';
  constructor(private http: HttpClient) { }
  findAll(): Observable<Quote[]> {
    return this.http.get<Quote[]>(this.apiUrl);
  }
}
