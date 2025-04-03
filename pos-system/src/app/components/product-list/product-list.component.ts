import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Product } from '../../models/product.model';
import { ProductService } from '../../services/product.service';
import { CartService } from '../../services/cart.service';
import { ProductCardComponent } from '../product-card/product-card.component';
import { SearchBarComponent } from '../search-bar/search-bar.component';
import { TableModule } from 'primeng/table';
import { PaginatorModule } from 'primeng/paginator';
import { ButtonModule } from 'primeng/button';
import { DialogModule } from 'primeng/dialog';
import { InputNumberModule } from 'primeng/inputnumber';
import { TagModule } from 'primeng/tag';

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ProductCardComponent,
    SearchBarComponent,
    TableModule,
    PaginatorModule,
    ButtonModule,
    DialogModule,
    InputNumberModule,
    TagModule
  ],
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.scss']
})
export class ProductListComponent implements OnInit {
  products: Product[] = [];
  totalRecords = 0;
  loading: boolean = false;
  rows = 10;
  first = 0;
  displayDialog = false;
  selectedProduct: Product = {} as Product;
  newProduct: Product = {
    id: 0,
    nombre: '',
    precio: 0,
    stock: 0,
    activo: true
  };

  constructor(
    private productService: ProductService,
    private cartService: CartService
  ) { }

  ngOnInit(): void {
    this.loadProducts();
  }

  loadProducts(): void {
    this.productService.getProducts(this.first / this.rows, this.rows)
      .subscribe(response => {
        this.products = response.content;
        this.totalRecords = response.totalElements;
      });
  }

  onPageChange(event: any): void {
    this.first = event.first;
    this.rows = event.rows;
    this.loadProducts();
  }

  searchProducts(term: string): void {
    if (term.trim()) {
      this.productService.searchProducts(term, this.first / this.rows, this.rows)
        .subscribe(response => {
          this.products = response.content;
          this.totalRecords = response.totalElements;
        });
    } else {
      this.loadProducts();
    }
  }

  addToCart(product: Product): void {
    this.cartService.addToCart(product);
  }

  showDialogToAdd(): void {
    this.newProduct = {
      id: 0,
      nombre: '',
      precio: 0,
      stock: 0,
      activo: true
    };
    this.selectedProduct = { ...this.newProduct };
    this.displayDialog = true;
  }

  showDialogToEdit(product: Product): void {
    this.selectedProduct = { ...product };
    this.displayDialog = true;
  }

  save(): void {
    if (this.selectedProduct.id) {
      this.productService.updateProduct(this.selectedProduct.id, this.selectedProduct)
        .subscribe(() => {
          this.loadProducts();
          this.displayDialog = false;
        });
    } else {
      this.productService.createProduct(this.selectedProduct)
        .subscribe(() => {
          this.loadProducts();
          this.displayDialog = false;
        });
    }
  }

  activateProduct(id: number): void {
    this.productService.activateProduct(id).subscribe(() => this.loadProducts());
  }

  deactivateProduct(id: number): void {
    this.productService.deactivateProduct(id).subscribe(() => this.loadProducts());
  }

  deleteProduct(id: number): void {
    this.productService.deleteProduct(id).subscribe(() => this.loadProducts());
  }
}