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
    if (request.id === undefined) {
      delete request.id;
    }

    if (request.minTotalPrice === undefined) {
      delete request.minTotalPrice;
    }

    return this.http.get<Page<ShoppingCart>>(`${environment.backendUrl}/shopping-cart/search`, {
      params: request as any
    });
  }
}
