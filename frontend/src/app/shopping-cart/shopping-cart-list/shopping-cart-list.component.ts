import {ChangeDetectorRef, Component, EventEmitter, Input, Output, ViewChild} from '@angular/core';
import {articleCount, ShoppingCart} from "../model/shopping-cart";
import {MatPaginator, MatPaginatorIntl, PageEvent} from "@angular/material/paginator";
import {emptyPage, Page, toEmptyPage} from "../../pagination/page";
import {Sort} from "@angular/material/sort";

@Component({
  selector: 'app-shopping-cart-list',
  templateUrl: './shopping-cart-list.component.html',
  styleUrl: './shopping-cart-list.component.scss'
})
export class ShoppingCartListComponent {

  @Input({
    transform: (value: Page<ShoppingCart> | null) => toEmptyPage(value)
  })
  public shoppingCarts: Page<ShoppingCart> = emptyPage();

  @Output()
  public pageChange: EventEmitter<PageEvent> = new EventEmitter<PageEvent>();

  @Output()
  public sortChange: EventEmitter<Sort> = new EventEmitter<Sort>();

  @ViewChild(MatPaginator) paginator: MatPaginator = new MatPaginator(
    new MatPaginatorIntl(), ChangeDetectorRef.prototype
  );

  public get displayedColumns(): string[] {
    return ['id', 'customer', 'articleCount', 'checkedOut', 'totalPrice'];
  }

  public get pageSizeOptions(): number[] {
    return [2, 5, 10, 100];
  }

  onPageChange($event: PageEvent) {
    this.pageChange.emit($event);
  }

  protected readonly articleCount = articleCount;

  onSortChange($event: Sort) {
    this.sortChange.emit($event);
  }
}
