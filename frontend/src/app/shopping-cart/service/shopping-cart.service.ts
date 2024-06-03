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

  // private _search: BehaviorSubject<ShoppingCartSearch> = new BehaviorSubject<ShoppingCartSearch>({});
  // private _shoppingCarts$: BehaviorSubject<Page<ShoppingCart>> = new BehaviorSubject<Page<ShoppingCart>>(emptyPage());

  constructor(private readonly http: HttpClient) {
    // this._search.subscribe(value => {
    //   this.http.get<Page<ShoppingCart>>(`${environment.backendUrl}/shopping-cart/search`, {
    //     params: value as any
    //   }).subscribe(value => this._shoppingCarts$.next(value));
    // });
  }

  //
  // public refresh(search: ShoppingCartSearch) {
  //   this._search.next(search);
  // }
  //
  // public get shoppingCarts$(): Observable<Page<ShoppingCart>> {
  //   return this._shoppingCarts$.asObservable();
  // }
  //
  // public get search$(): Observable<ShoppingCartSearch> {
  //   return this._search.asObservable();
  // }

  public search(request: ShoppingCartSearch): Observable<Page<ShoppingCart>> {
    return this.http.get<Page<ShoppingCart>>(`${environment.backendUrl}/shopping-cart/search`, {
      params: request as any
    });
  }
}
