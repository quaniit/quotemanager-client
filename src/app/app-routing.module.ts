import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { QuoteListComponent } from './quote-list/quote-list.component';

const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'dashboard', component: QuoteListComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
