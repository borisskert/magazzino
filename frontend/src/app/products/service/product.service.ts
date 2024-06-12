import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Product} from "../../shopping-cart/model/product";
import {Page} from "../../pagination/page";
import {ProductSearch} from "../model/product-search";

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private readonly httpClient: HttpClient) {
  }

  public search(search: ProductSearch) {
    return this.httpClient.post<Page<Product>>('/api/products/search', sanitize(search));
  }
}

function sanitize(search: ProductSearch): ProductSearch {
  const sanitized: ProductSearch = {...search};

  if (search.name === undefined) {
    delete sanitized.name;
  }

  if (search.number === undefined) {
    delete sanitized.number;
  }

  if (search.description === undefined) {
    delete sanitized.description;
  }

  return sanitized;
}
