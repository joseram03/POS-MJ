import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { NavbarComponent } from '../navbar/navbar.component';
import { ProductListComponent } from '../product-list/product-list.component';
import { CartSummaryComponent } from '../cart-summary/cart-summary.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    NavbarComponent,
    ProductListComponent,
    CartSummaryComponent
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
  private router = inject(Router);
}