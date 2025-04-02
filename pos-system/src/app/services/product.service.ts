import { Injectable } from '@angular/core';
import { Product } from '../models/product.model';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private products: Product[] = [
    { id: 1, name: 'Producto 1', price: 25000, imageUrl: '/assets/images/placeholder.jpg' },
    { id: 2, name: 'Producto 2', price: 15000, imageUrl: '/assets/images/placeholder.jpg' },
    { id: 3, name: 'Producto 3', price: 30000, imageUrl: '/assets/images/placeholder.jpg' },
    { id: 4, name: 'Producto 4', price: 10000, imageUrl: '/assets/images/placeholder.jpg' },
    { id: 5, name: 'Producto 5', price: 20000, imageUrl: '/assets/images/placeholder.jpg' },
    { id: 6, name: 'Producto 6', price: 18000, imageUrl: '/assets/images/placeholder.jpg' }
  ];

  constructor() { }

  getProducts(): Observable<Product[]> {
    return of(this.products);
  }

  searchProducts(term: string): Observable<Product[]> {
    const filteredProducts = this.products.filter(product => 
      product.name.toLowerCase().includes(term.toLowerCase())
    );
    return of(filteredProducts);
  }
}