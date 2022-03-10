import { Injectable } from '@angular/core';
import { Quote } from './quote';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, Observable, of, tap } from 'rxjs';
import { MessageService } from './message.service';


@Injectable()
export class QuoteService {
  private apiUrl = '/api/quotes';
  

  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  constructor(private http: HttpClient,
    private messageService: MessageService) { }
  findAll(): Observable<Quote[]> {
    return this.http.get<Quote[]>(this.apiUrl);
  }
  searchQuotes(term: String): Observable<Quote[]> {
    if(!term.trim()) {
      return of([]);
    }
    return this.http.get<Quote[]>('${this.apiUrl}/symbol/${term}');

  }
  deleteQuote(id: String): Observable<Quote>{
    const url = '${this.apiUrl}/id/${id}';
    return this.http.delete<Quote>(url, this.httpOptions).pipe(
      tap(_ => this.messageService.add("Quote Successfully Deleted")),
      catchError((error:any): Observable<any> => {
        console.error(error);
        this.messageService.add("Delete Failed: ${error.message}");
        return of([]);
      })
    );

  }
}
