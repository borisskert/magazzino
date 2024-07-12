import {Component} from '@angular/core';
import {ProductDataSource} from "../service/product-data-source";
import {Observable} from "rxjs";
import {Page} from "../../pagination/page";
import {ProductSearch} from "../model/product-search";
import {PageEvent} from "@angular/material/paginator";
import {Sort} from "@angular/material/sort";
import {Product} from "../model/product";
import {SnackbarService} from "../../snackbar/snackbar.service";
import {ProductDialogService} from "../service/product-dialog.service";

@Component({
  selector: 'app-products-container',
  templateUrl: './products-container.component.html',
  styleUrl: './products-container.component.scss'
})
export class ProductsContainerComponent {

  constructor(
    private readonly dataSource: ProductDataSource,
    private readonly snackbarService: SnackbarService,
    private readonly productDialogService: ProductDialogService,
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

  onOpenCreateDialog() {
    console.log('open create dialog')
    this.productDialogService.openNewProductDialog().subscribe(
      {
        next: (product: Product) => this.onCreate(product)
      }
    );
  }

  onOpenEditDialog($event: Product) {
    console.log('open edit dialog')
    this.productDialogService.openEditProductDialog($event).subscribe(
      {
        next: (product: Product) => this.onEdit(product)
      }
    );
  }

  private onCreate(product: Product) {
    this.dataSource.create(product).subscribe(
      {
        next: () => this.productDialogService.closeDialog(),
        error: () => this.snackbarService.show('Failed to create product. Try again later.')
      }
    );
  }

  private onEdit(product: Product) {
    this.dataSource.update(product).subscribe(
      {
        next: () => this.productDialogService.closeDialog(),
        error: () => this.snackbarService.show('Failed to update product. Try again later.')
      }
    );
  }
}
