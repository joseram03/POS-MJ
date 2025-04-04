import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Product } from '../models/product.model';
import { environment } from '../../environments/environment';

interface PaginatedResponse {
  content: Product[];
  totalElements: number;
  // Puedes agregar más campos según la respuesta de tu backend
}

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private apiUrl = `${environment.apiUrl}/api/productos`;

  constructor(private http: HttpClient) { }

  getProducts(page: number = 0, limit: number = 10): Observable<PaginatedResponse> {
    return this.http.get<PaginatedResponse>(this.apiUrl, {
      params: { 
        page: page.toString(), 
        limit: limit.toString() 
      }
    });
  }

  searchProducts(term: string, page: number = 0, limit: number = 10): Observable<PaginatedResponse> {
    return this.http.get<PaginatedResponse>(`${this.apiUrl}/search`, {
      params: { 
        term,
        page: page.toString(),
        limit: limit.toString()
      }
    });
  }

  createProduct(product: Product): Observable<Product> {
    return this.http.post<Product>(this.apiUrl, product);
  }

  updateProduct(id: number, product: Product): Observable<Product> {
    return this.http.put<Product>(`${this.apiUrl}/${id}`, product);
  }

  activateProduct(id: number): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/${id}/activar`, {});
  }

  deactivateProduct(id: number): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/${id}/desactivar`, {});
  }

  deleteProduct(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}