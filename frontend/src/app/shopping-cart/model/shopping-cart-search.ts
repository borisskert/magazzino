import {PageRequest} from "../../pagination/page-request";
import {ShoppingCart} from "./shopping-cart";

export interface ShoppingCartSearch extends PageRequest<ShoppingCart> {
  id?: number;
  productNumber?: string;
  productName?: string;
  minTotalPrice?: number;
}

export function defaultShoppingCartSearch(): ShoppingCartSearch {
  return {
    page: 0,
    size: 10
  };
}

export function toDefaultShoppingCartSearch(value: ShoppingCartSearch | null): ShoppingCartSearch {
  return value === null ? defaultShoppingCartSearch() : value;
}
