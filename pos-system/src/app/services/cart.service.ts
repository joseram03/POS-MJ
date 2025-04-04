import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { Product } from '../models/product.model';
import { CartItem } from '../models/cart-item.model';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { HttpHeaders } from '@angular/common/http';

interface VentaRequest {
  venta: {
    metodoPago: string;
    usuario: {
      id: number;
    };
  };
  detalles: Array<{
    productoId: number;
    cantidad: number;
  }>;
}

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private apiUrl = `${environment.apiUrl}/api/ventas`;
  private cartItems: CartItem[] = [];
  private cartSubject = new BehaviorSubject<CartItem[]>([]);
  private totalSubject = new BehaviorSubject<number>(0);

  constructor(private http: HttpClient) { }

  getCartItems(): Observable<CartItem[]> {
    return this.cartSubject.asObservable();
  }

  getTotal(): Observable<number> {
    return this.totalSubject.asObservable();
  }

  addToCart(product: Product): void {
    const existingItem = this.cartItems.find(item => item.product.id === product.id);
    
    if (existingItem) {
      existingItem.quantity += 1;
    } else {
      this.cartItems.push({ product, quantity: 1 });
    }
    
    this.updateCart();
  }

  removeFromCart(product: Product): void {
    const index = this.cartItems.findIndex(item => item.product.id === product.id);
    
    if (index !== -1) {
      this.cartItems.splice(index, 1);
      this.updateCart();
    }
  }

  updateQuantity(product: Product, quantity: number): void {
    const item = this.cartItems.find(item => item.product.id === product.id);
    
    if (item) {
      item.quantity = quantity;
      
      if (item.quantity <= 0) {
        this.removeFromCart(product);
      } else {
        this.updateCart();
      }
    }
  }

  clearCart(): void {
    this.cartItems = [];
    this.updateCart();
  }

  private updateCart(): void {
    this.cartSubject.next([...this.cartItems]);
    this.calculateTotal();
  }

  private calculateTotal(): void {
    const total = this.cartItems.reduce(
      (sum, item) => sum + (item.product.precio * item.quantity), 
      0
    );
    this.totalSubject.next(total);
  }

  confirmarVenta(metodoPago: string, usuarioId: number): Observable<any> {
    const ventaRequest = {
      venta: {
        metodoPago,
        usuario: { id: usuarioId }
      },
      detalles: this.cartItems.map(item => ({
        productoId: item.product.id,
        cantidad: item.quantity
      }))
    };
  
    // El interceptor añadirá automáticamente el token
    return this.http.post(this.apiUrl, ventaRequest);
  }
}