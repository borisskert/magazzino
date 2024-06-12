import {defaultPageRequest, PageRequest} from "../../pagination/page-request";
import {Product} from "../../shopping-cart/model/product";

export interface ProductSearch extends PageRequest<Product> {
  name?: string;
  number?: string;
  description?: string;
}

export function defaultProductSearch(): ProductSearch {
  return {...defaultPageRequest()};
}
