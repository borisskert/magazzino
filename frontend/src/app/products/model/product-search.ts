import {PageRequest} from "../../pagination/page-request";

export interface ProductSearch extends PageRequest {
  name?: string;
  number?: string;
  description?: string;
}

export function defaultProductSearch(): ProductSearch {
  return {
    page: 0,
    size: 10,
  };
}
