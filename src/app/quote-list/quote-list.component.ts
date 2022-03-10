import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { QuoteService } from '../quote.service';
import { Quote } from '../quote'; 

@Component({
  selector: 'app-quote-list',
  templateUrl: './quote-list.component.html',
  styleUrls: ['./quote-list.component.css'],
  providers: [QuoteService]
})
export class QuoteListComponent implements OnInit {
  quotes: Quote[] = [];

  constructor(private router: Router,
    private quoteService: QuoteService) { }

  ngOnInit() {
    this.getAllQuotes();
  }

  getAllQuotes() {
    this.quoteService.findAll().subscribe(
      result => {
      this.quotes = result;
      }
    )
  }
}
