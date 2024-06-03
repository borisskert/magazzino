import {Component, Input} from '@angular/core';
import {emptyShoppingCart, ShoppingCart} from "../model/shopping-cart";

@Component({
  selector: 'app-shopping-cart-list-item',
  standalone: true,
  imports: [],
  templateUrl: './shopping-cart-list-item.component.html',
  styleUrl: './shopping-cart-list-item.component.scss'
})
export class ShoppingCartListItemComponent {

  @Input()
  public shoppingCart: ShoppingCart = emptyShoppingCart();
}
