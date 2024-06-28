import {Component} from '@angular/core';
import {RouterLink, RouterOutlet} from '@angular/router';
import {ShoppingCartModule} from "./shopping-cart/shopping-cart.module";
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatListModule, MatNavList} from "@angular/material/list";
import {ProductsModule} from "./products/products-module";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet,

    ShoppingCartModule,
    ProductsModule,

    MatSidenavModule,
    MatNavList,
    MatListModule,
    RouterLink,
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
}
