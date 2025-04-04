import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { DropdownModule } from 'primeng/dropdown';
import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';
import { CartItem } from '../../models/cart-item.model';
import { CartService } from '../../services/cart.service';
import { CardModule } from 'primeng/card';
import { MessageService } from 'primeng/api';
import { ToastModule } from 'primeng/toast';

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
    CardModule,
    ToastModule
  ],
  templateUrl: './cart-summary.component.html',
  styleUrls: ['./cart-summary.component.scss']
})
export class CartSummaryComponent implements OnInit {
  cartItems: CartItem[] = [];
  total: number = 0;
  paymentMethods: PaymentMethod[] = [];
  selectedPaymentMethod: PaymentMethod | undefined;
  loading = false;

  constructor(
    private cartService: CartService,
    private messageService: MessageService
  ) { }

  ngOnInit(): void {
    this.paymentMethods = [
      { name: 'Efectivo', code: 'EFECTIVO' },
      { name: 'Tarjeta', code: 'TARJETA' },
      { name: 'Transferencia', code: 'TRANSFERENCIA' }
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
    if (this.cartItems.length === 0) {
      this.messageService.add({
        severity: 'warn',
        summary: 'Carrito vacío',
        detail: 'Agregue productos al carrito antes de confirmar'
      });
      return;
    }

    if (!this.selectedPaymentMethod) {
      this.messageService.add({
        severity: 'warn',
        summary: 'Método de pago requerido',
        detail: 'Seleccione un método de pago'
      });
      return;
    }

    this.loading = true;
    
    // Aquí puedes obtener el ID del usuario autenticado de tu servicio de autenticación
    // Por ahora lo dejamos como 1 para pruebas
    const usuarioId = 2; 

    this.cartService.confirmarVenta(this.selectedPaymentMethod.code, usuarioId)
      .subscribe({
        next: () => {
          this.messageService.add({
            severity: 'success',
            summary: 'Venta confirmada',
            detail: 'La venta se ha registrado correctamente'
          });
          this.cartService.clearCart();
          this.loading = false;
        },
        error: (err) => {
          console.error('Error al confirmar venta:', err);
          this.messageService.add({
            severity: 'error',
            summary: 'Error',
            detail: 'Ocurrió un error al confirmar la venta'
          });
          this.loading = false;
        }
      });
  }
}