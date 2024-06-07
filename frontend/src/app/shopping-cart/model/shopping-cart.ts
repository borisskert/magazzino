import {Customer} from "./customer";
import {ShoppingCartItem} from "./shopping-cart-item";

export interface ShoppingCart {
  id: number;
  customer: Customer;
  items: ShoppingCartItem[];
  checkedOut: boolean;
  totalPrice: number;
}

export function articleCount(cart: ShoppingCart): number {
  return cart.items
    .map(item => item.quantity)
    .reduce((prev, curr) => prev + curr, 0);
}
