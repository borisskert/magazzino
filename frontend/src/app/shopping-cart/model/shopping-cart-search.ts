import {PageRequest} from "../../pagination/page-request";

export interface ShoppingCartSearch extends PageRequest {
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
