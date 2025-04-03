import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CardModule } from 'primeng/card';
import { InputTextModule } from 'primeng/inputtext';
import { PasswordModule } from 'primeng/password';
import { ButtonModule } from 'primeng/button';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CardModule, InputTextModule, FormsModule, PasswordModule, ButtonModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
  providers: [MessageService]
})
export class LoginComponent {
  private authService = inject(AuthService);
  private router = inject(Router);
  private messageService = inject(MessageService);

  login = { usuario: '', contrasenha: '' };

  onLogin() {
    const { usuario, contrasenha } = this.login;
    
    this.authService.loginUser({ usuario, contrasenha }).subscribe({
      next: (response) => {
        if (response.status === 200) {
          sessionStorage.setItem('token', response.data.token);
          sessionStorage.setItem('usuario', response.data.usuario);
          sessionStorage.setItem('rol', response.data.rol);
          this.router.navigate(['home']);
        } else {
          this.messageService.add({
            severity: 'error',
            summary: 'Error',
            detail: response.mensaje,
          });
        }
      },
      error: () => {
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'Usuario o contraseña incorrectos',
        });
      },
    });
  }
}
