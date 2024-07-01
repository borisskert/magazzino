import {Product} from "../../products/model/product";

export interface ShoppingCartItem {
  product: Product;
  quantity: number;
}
