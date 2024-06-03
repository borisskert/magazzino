import {Customer, emptyCustomer} from "./customer";
import {ShoppingCartItem} from "./shopping-cart-item";

export interface ShoppingCart {
  id: number;
  customer: Customer;
  items: ShoppingCartItem[];
  checkedOut: boolean;
  totalPrice: number;
}

export function emptyShoppingCart(): ShoppingCart {
  return {
    id: 0,
    customer: emptyCustomer(),
    items: [],
    checkedOut: false,
    totalPrice: 0,
  };
}
