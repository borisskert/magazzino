import {Injectable} from "@angular/core";
import {CollectionViewer, DataSource} from "@angular/cdk/collections";
import {Product} from "../../shopping-cart/model/product";
import {BehaviorSubject, debounceTime, map, Observable, switchMap} from "rxjs";
import {emptyPage, Page} from "../../pagination/page";
import {defaultProductSearch, ProductSearch} from "../model/product-search";
import {ProductService} from "./product.service";
import {Sort} from "@angular/material/sort";

@Injectable({
  providedIn: 'root'
})
export class ProductDataSource implements DataSource<Product> {

  private readonly _search: BehaviorSubject<ProductSearch> = new BehaviorSubject<ProductSearch>(defaultProductSearch());
  private readonly _products$: BehaviorSubject<Page<Product>> = new BehaviorSubject<Page<Product>>(emptyPage());

  constructor(private readonly service: ProductService) {
    this._search.pipe(
      debounceTime(200),
      switchMap(search => this.service.search(search))
    ).subscribe(value => this._products$.next(value))
  }

  public get search$(): Observable<ProductSearch> {
    return this._search.asObservable();
  }

  public get products$(): Observable<Page<Product>> {
    return this._products$.asObservable();
  }

  public refreshSearch(search: ProductSearch = defaultProductSearch()) {
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

  connect(_: CollectionViewer): Observable<readonly Product[]> {
    return this._products$.asObservable().pipe(
      map(page => page.content)
    );
  }

  disconnect(_: CollectionViewer): void {
    this._products$.complete();
    this._search.complete();
  }
}
