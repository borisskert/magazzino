import {CollectionViewer, DataSource} from "@angular/cdk/collections";
import {ShoppingCart} from "../model/shopping-cart";
import {Injectable} from "@angular/core";
import {BehaviorSubject, debounceTime, map, Observable, switchMap} from "rxjs";
import {defaultShoppingCartSearch, ShoppingCartSearch} from "../model/shopping-cart-search";
import {emptyPage, Page} from "../../pagination/page";
import {ShoppingCartService} from "./shopping-cart.service";
import {Sort} from "@angular/material/sort";

@Injectable({
  providedIn: 'root'
})
export class ShoppingCartDataSource implements DataSource<ShoppingCart> {

  private _search: BehaviorSubject<ShoppingCartSearch> = new BehaviorSubject<ShoppingCartSearch>(defaultShoppingCartSearch());
  private _shoppingCarts$: BehaviorSubject<Page<ShoppingCart>> = new BehaviorSubject<Page<ShoppingCart>>(emptyPage());

  constructor(private readonly shoppingCartService: ShoppingCartService) {
    this._search.pipe(
      debounceTime(200),
      switchMap(search => this.shoppingCartService.search(search))
    ).subscribe(value => this._shoppingCarts$.next(value))
  }

  public get search$(): Observable<ShoppingCartSearch> {
    return this._search.asObservable();
  }

  public get shoppingCarts$(): Observable<Page<ShoppingCart>> {
    return this._shoppingCarts$.asObservable();
  }


  public refreshSearch(search: ShoppingCartSearch = defaultShoppingCartSearch()) {
    let newSearch = {
      ...this._search.getValue(),
      ...search
    };

    this._search.next(newSearch);
  }

  public refreshSort(sort: Sort) {
    this.refreshSearch({
      ...this._search.getValue(),
      sort: sort
    });
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
