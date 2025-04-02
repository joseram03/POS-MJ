import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MenuItem } from 'primeng/api';
import { TabMenuModule } from 'primeng/tabmenu';
import { AvatarModule } from 'primeng/avatar';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, TabMenuModule, AvatarModule],
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {
  items: MenuItem[] = [];
  activeItem: MenuItem = {};

  ngOnInit() {
    this.items = [
      { label: 'Productos', icon: 'pi pi-box' },
      { label: 'Ventas', icon: 'pi pi-shopping-cart' }
    ];
    this.activeItem = this.items[0];
  }
}