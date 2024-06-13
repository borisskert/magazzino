import {ChangeDetectorRef, Component, EventEmitter, Input, Output, ViewChild} from '@angular/core';
import {Product} from "../../shopping-cart/model/product";
import {emptyPage, Page, toEmptyPage} from "../../pagination/page";
import {MatPaginator, MatPaginatorIntl, PageEvent} from "@angular/material/paginator";

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

  @ViewChild(MatPaginator) paginator: MatPaginator = new MatPaginator(
    new MatPaginatorIntl(), ChangeDetectorRef.prototype
  );

  public get displayedColumns(): string[] {
    return ['number', 'name', 'price'];
  }

  public get pageSizeOptions(): number[] {
    return [2, 5, 10, 100];
  }

  onPageChange($event: PageEvent) {
    this.pageChange.emit($event);
  }
}
