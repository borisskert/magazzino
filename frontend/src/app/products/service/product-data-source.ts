import {Injectable} from "@angular/core";
import {CollectionViewer, DataSource} from "@angular/cdk/collections";
import {BehaviorSubject, combineLatest, debounceTime, map, Observable, switchMap} from "rxjs";
import {emptyPage, Page} from "../../pagination/page";
import {defaultProductSearch, ProductSearch} from "../model/product-search";
import {ProductService} from "./product.service";
import {Sort} from "@angular/material/sort";
import {Product} from "../model/product";

@Injectable({
  providedIn: 'root'
})
export class ProductDataSource implements DataSource<Product> {

  private readonly _search: BehaviorSubject<ProductSearch> = new BehaviorSubject<ProductSearch>(defaultProductSearch());
  private readonly _sort: BehaviorSubject<Sort> = new BehaviorSubject<Sort>({active: 'id', direction: 'asc'});
  private readonly _products$: BehaviorSubject<Page<Product>> = new BehaviorSubject<Page<Product>>(emptyPage());

  constructor(private readonly service: ProductService) {
    this.setupSearchSubscription();
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

  public create(product: Product) {
    return this.service.create(product).pipe(
      switchMap(() => this.refresh())
    );
  }

  public update(product: Product) {
    return this.service.update(product).pipe(
      switchMap(() => this.refresh())
    );
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

  private refresh(): Observable<void> {
    return this.service.search(this._search.getValue()).pipe(
      map(page => this._products$.next(page))
    )
  }

  private setupSearchSubscription() {
    combineLatest([
      this._search,
      this._sort
    ]).pipe(
      debounceTime(200),
      switchMap(([search, sort]) => this.service.search({
        ...search,
        sort: sort
      }))
    ).subscribe(page => this._products$.next(page));
  }
}
