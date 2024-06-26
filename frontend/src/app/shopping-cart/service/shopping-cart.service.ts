import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ShoppingCartSearch} from "../model/shopping-cart-search";
import {Observable} from "rxjs";
import {Page} from "../../pagination/page";
import {ShoppingCart} from "../model/shopping-cart";
import {environment} from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class ShoppingCartService {

  constructor(private readonly http: HttpClient) {
  }

  public search(request: ShoppingCartSearch): Observable<Page<ShoppingCart>> {
    return this.http.get<Page<ShoppingCart>>(`${environment.backendUrl}/api/shopping-cart/search`, {
      params: sanitize(request),
    });
  }
}

function sanitize(request: ShoppingCartSearch): any {
  const sanitized: any = {...request};

  if (request.id === undefined) {
    delete sanitized.id;
  }

  if (request.productName === undefined) {
    delete sanitized.productName;
  }

  if (request.productNumber === undefined) {
    delete sanitized.productNumber;
  }

  if (request.minTotalPrice === undefined) {
    delete sanitized.minTotalPrice;
  }

  if (request.sort) {
    sanitized.sort = `${request.sort.active},${request.sort.direction}`;
  }

  return sanitized;
}
