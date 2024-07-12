import {ChangeDetectorRef, Component, EventEmitter, Input, Output, ViewChild} from '@angular/core';
import {emptyPage, Page, toEmptyPage} from "../../pagination/page";
import {MatPaginator, MatPaginatorIntl, PageEvent} from "@angular/material/paginator";
import {Sort} from "@angular/material/sort";
import {Product} from "../model/product";

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrl: './product-list.component.scss'
})
export class ProductListComponent {

  @Input({
    transform: (value: Page<Product> | null) => toEmptyPage(value)
  })
  public products: Page<Product> = emptyPage();

  @Output()
  public pageChange: EventEmitter<PageEvent> = new EventEmitter<PageEvent>();

  @Output()
  public sortChange: EventEmitter<Sort> = new EventEmitter<Sort>();

  @Output()
  public edit: EventEmitter<Product> = new EventEmitter<Product>();

  @ViewChild(MatPaginator) paginator: MatPaginator = new MatPaginator(
    new MatPaginatorIntl(), ChangeDetectorRef.prototype
  );

  public get displayedColumns(): string[] {
    return ['number', 'name', 'price', 'actions'];
  }

  public get pageSizeOptions(): number[] {
    return [2, 5, 10, 100];
  }

  onPageChange($event: PageEvent) {
    this.pageChange.emit($event);
  }

  onSortChange($event: Sort) {
    this.sortChange.emit($event);
  }

  onEdit(product: Product) {
    this.edit.emit(product);
  }
}
