import {Component} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {ShoppingCartModule} from "./shopping-cart/shopping-cart.module";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet,
    ShoppingCartModule,
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
}
