import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { DropdownModule } from 'primeng/dropdown';
import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';
import { CartItem } from '../../models/cart-item.model';
import { CartService } from '../../services/cart.service';
import { CardModule } from 'primeng/card';

interface PaymentMethod {
  name: string;
  code: string;
}

@Component({
  selector: 'app-cart-summary',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    DropdownModule,
    ButtonModule,
    TableModule,
    CardModule
  ],
  templateUrl: './cart-summary.component.html',
  styleUrls: ['./cart-summary.component.scss']
})
export class CartSummaryComponent implements OnInit {
  cartItems: CartItem[] = [];
  total: number = 0;
  paymentMethods: PaymentMethod[] = [];
  selectedPaymentMethod: PaymentMethod | undefined;

  constructor(private cartService: CartService) { }

  ngOnInit(): void {
    this.paymentMethods = [
      { name: 'Efectivo', code: 'cash' },
      { name: 'Tarjeta de Crédito', code: 'credit' },
      { name: 'Tarjeta de Débito', code: 'debit' },
      { name: 'Transferencia', code: 'transfer' }
    ];

    this.cartService.getCartItems().subscribe(items => {
      this.cartItems = items;
    });

    this.cartService.getTotal().subscribe(total => {
      this.total = total;
    });
  }

  increaseQuantity(item: CartItem): void {
    this.cartService.updateQuantity(item.product, item.quantity + 1);
  }

  decreaseQuantity(item: CartItem): void {
    if (item.quantity > 1) {
      this.cartService.updateQuantity(item.product, item.quantity - 1);
    }
  }

  removeItem(item: CartItem): void {
    this.cartService.removeFromCart(item.product);
  }

  confirmSale(): void {
    if (this.cartItems.length > 0 && this.selectedPaymentMethod) {
      alert('Venta confirmada con método de pago: ' + this.selectedPaymentMethod.name);
      this.cartService.clearCart();
    } else {
      alert('Agregue productos al carrito y seleccione un método de pago');
    }
  }
}