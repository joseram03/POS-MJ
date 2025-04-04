import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MenuItem } from 'primeng/api';
import { TabMenuModule } from 'primeng/tabmenu';
import { AvatarModule } from 'primeng/avatar';
import { ButtonModule } from 'primeng/button';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, TabMenuModule, AvatarModule, ButtonModule],
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {
  items: MenuItem[] = [];
  activeItem: MenuItem = {};

  constructor(private router: Router) {}

  ngOnInit() {
    this.items = [
      { label: 'Productos', icon: 'pi pi-box' }
    ];
    this.activeItem = this.items[0];
  }

  logout(): void {
    // Remueve el token de sessionStorage
    sessionStorage.removeItem('token');
    // Redirige a la página de login (ajusta la ruta según tu aplicación)
    this.router.navigate(['login']);
  }
}
