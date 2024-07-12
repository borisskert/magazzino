import {Component} from '@angular/core';
import {ProductDataSource} from "../service/product-data-source";
import {Observable} from "rxjs";
import {Page} from "../../pagination/page";
import {ProductSearch} from "../model/product-search";
import {PageEvent} from "@angular/material/paginator";
import {Sort} from "@angular/material/sort";
import {Product} from "../model/product";

@Component({
  selector: 'app-products-container',
  templateUrl: './products-container.component.html',
  styleUrl: './products-container.component.scss'
})
export class ProductsContainerComponent {

  constructor(private readonly dataSource: ProductDataSource) {
  }

  public get search$(): Observable<ProductSearch> {
    return this.dataSource.search$;
  }

  public get products$(): Observable<Page<Product>> {
    return this.dataSource.products$;
  }

  onPageChange($event: PageEvent) {
    this.dataSource.refreshSearch({
        page: $event.pageIndex,
        size: $event.pageSize,
      }
    )
  }

  onSearchChange($event: ProductSearch) {
    this.dataSource.refreshSearch($event);
  }

  onSortChange($event: Sort) {
    this.dataSource.refreshSort($event);
  }

  onCreate($event: Product) {
    this.dataSource.create($event).subscribe(
      {
        next: () => console.log('Product created'),
        error: () => console.error('Failed to create product')
      }
    );
  }

  onEdit($event: Product) {
    this.dataSource.update($event).subscribe(
      {
        next: () => console.log('Product updated'),
        error: () => console.error('Failed to update product')
      }
    );
  }
}
