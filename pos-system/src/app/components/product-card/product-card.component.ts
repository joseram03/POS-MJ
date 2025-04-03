// src/app/components/product-card/product-card.component.ts
import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { Product } from '../../models/product.model';
import { TagModule } from 'primeng/tag';

@Component({
  selector: 'app-product-card',
  standalone: true,
  imports: [CommonModule, CardModule, ButtonModule, TagModule],
  templateUrl: './product-card.component.html',
  styleUrls: ['./product-card.component.scss']
})
export class ProductCardComponent {
  @Input() product!: Product;
  @Output() addToCart = new EventEmitter<Product>();
  @Output() edit = new EventEmitter<Product>();
  @Output() toggleStatus = new EventEmitter<number>();
  @Output() delete = new EventEmitter<number>();

  onAddToCart() {
    this.addToCart.emit(this.product);
  }

  onEdit() {
    this.edit.emit(this.product);
  }

  onToggleStatus() {
    this.toggleStatus.emit(this.product.id);
  }

  onDelete() {
    this.delete.emit(this.product.id);
  }

  getStatusSeverity() {
    return this.product.activo ? 'success' : 'danger';
  }

  getStatusLabel() {
    return this.product.activo ? 'Activo' : 'Inactivo';
  }
}