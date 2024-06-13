import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Product} from "../../shopping-cart/model/product";
import {Page} from "../../pagination/page";
import {ProductSearch} from "../model/product-search";
import {environment} from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private readonly httpClient: HttpClient) {
  }

  public search(search: ProductSearch) {
    return this.httpClient.get<Page<Product>>(`${environment.backendUrl}/api/products/search`, {
      params: sanitize(search) as any,
    });
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
