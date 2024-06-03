import {CollectionViewer, DataSource} from "@angular/cdk/collections";
import {ShoppingCart} from "../model/shopping-cart";
import {Injectable} from "@angular/core";
import {BehaviorSubject, map, Observable} from "rxjs";
import {defaultShoppingCartSearch, ShoppingCartSearch} from "../model/shopping-cart-search";
import {emptyPage, Page} from "../../pagination/page";
import {ShoppingCartService} from "./shopping-cart.service";

@Injectable({
  providedIn: 'root'
})
export class ShoppingCartDataSource implements DataSource<ShoppingCart> {

  private _search: BehaviorSubject<ShoppingCartSearch> = new BehaviorSubject<ShoppingCartSearch>(defaultShoppingCartSearch());
  private _shoppingCarts$: BehaviorSubject<Page<ShoppingCart>> = new BehaviorSubject<Page<ShoppingCart>>(emptyPage());

  constructor(private readonly shoppingCartService: ShoppingCartService) {
    this._search.subscribe(value => {
      this.shoppingCartService.search(value)
        .subscribe(
          value => this._shoppingCarts$.next(value)
        );
    });
  }

  public refresh(search: ShoppingCartSearch = defaultShoppingCartSearch()) {
    let newSearch = {
      ...this._search.getValue(),
      ...search
    };

    this._search.next(newSearch);
  }

  public get search$(): Observable<ShoppingCartSearch> {
    return this._search.asObservable();
  }

  public get shoppingCarts$(): Observable<Page<ShoppingCart>> {
    return this._shoppingCarts$.asObservable();
  }

  connect(_: CollectionViewer): Observable<readonly ShoppingCart[]> {
    return this._shoppingCarts$.asObservable().pipe(
      map(page => page.content)
    );
  }

  disconnect(_: CollectionViewer): void {
    this._shoppingCarts$.complete();
    this._search.complete();
  }
}
