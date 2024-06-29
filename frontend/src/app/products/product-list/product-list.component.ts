import {ChangeDetectorRef, Component, EventEmitter, Input, Output, ViewChild} from '@angular/core';
import {Product} from "../../shopping-cart/model/product";
import {emptyPage, Page, toEmptyPage} from "../../pagination/page";
import {MatPaginator, MatPaginatorIntl, PageEvent} from "@angular/material/paginator";
import {Sort} from "@angular/material/sort";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {EditProductDialogComponent} from "../edit-product-dialog/edit-product-dialog.component";

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

  @ViewChild(MatPaginator) paginator: MatPaginator = new MatPaginator(
    new MatPaginatorIntl(), ChangeDetectorRef.prototype
  );

  constructor(private readonly dialog: MatDialog) {
  }

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
    const dialogConfig = new MatDialogConfig();
    dialogConfig.panelClass = 'edit-product-dialog-container';

    this.dialog.open(EditProductDialogComponent, {
      data: product,
    });
  }
}
