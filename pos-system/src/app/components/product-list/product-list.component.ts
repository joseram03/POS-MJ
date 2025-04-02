import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Product } from '../../models/product.model';
import { ProductService } from '../../services/product.service';
import { CartService } from '../../services/cart.service';
import { ProductCardComponent } from '../product-card/product-card.component';
import { SearchBarComponent } from '../search-bar/search-bar.component';
import { TableModule } from 'primeng/table';

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [
    CommonModule,
    ProductCardComponent,
    SearchBarComponent,
    TableModule
  ],
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.scss']
})
export class ProductListComponent implements OnInit {
  products: Product[] = [];

  constructor(
    private productService: ProductService,
    private cartService: CartService
  ) { }

  ngOnInit(): void {
    this.loadProducts();
  }

  loadProducts(): void {
    this.productService.getProducts().subscribe(products => {
      this.products = products;
    });
  }

  searchProducts(term: string): void {
    if (term.trim()) {
      this.productService.searchProducts(term).subscribe(products => {
        this.products = products;
      });
    } else {
      this.loadProducts();
    }
  }

  addToCart(product: Product): void {
    this.cartService.addToCart(product);
  }
}