import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Page} from "../../pagination/page";
import {ProductSearch} from "../model/product-search";
import {environment} from "../../../environments/environment";
import {Product} from "../model/product";

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private readonly httpClient: HttpClient) {
  }

  public search(search: ProductSearch) {
    return this.httpClient.get<Page<Product>>(`${environment.backendUrl}/api/products/search`, {
      params: {
        ...sanitize(search),
      } as any,
    });
  }

  public create(product: Product) {
    return this.httpClient.post<Product>(`${environment.backendUrl}/api/products`, product);
  }
}

function sanitize(search: ProductSearch): any {
  const sanitized: any = {
    ...search,
  };

  if (search.name === undefined) {
    delete sanitized.name;
  }

  if (search.number === undefined) {
    delete sanitized.number;
  }

  if (search.description === undefined) {
    delete sanitized.description;
  }

  if (search.sort) {
    sanitized.sort = `${search.sort.active},${search.sort.direction}`
  }

  return sanitized;
}
