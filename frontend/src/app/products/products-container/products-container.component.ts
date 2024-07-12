import {Component} from '@angular/core';
import {ProductDataSource} from "../service/product-data-source";
import {Observable} from "rxjs";
import {Page} from "../../pagination/page";
import {ProductSearch} from "../model/product-search";
import {PageEvent} from "@angular/material/paginator";
import {Sort} from "@angular/material/sort";
import {Product} from "../model/product";
import {SnackbarService} from "../../snackbar/snackbar.service";

@Component({
  selector: 'app-products-container',
  templateUrl: './products-container.component.html',
  styleUrl: './products-container.component.scss'
})
export class ProductsContainerComponent {

  constructor(
    private readonly dataSource: ProductDataSource,
    private readonly snackbarService: SnackbarService,
  ) {
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
        error: () => this.snackbarService.show('Failed to create product. Try again later.')
      }
    );
  }

  onEdit($event: Product) {
    this.dataSource.update($event).subscribe(
      {
        next: () => console.log('Product updated'),
        error: () => this.snackbarService.show('Failed to update product. Try again later.')
      }
    );
  }
}
