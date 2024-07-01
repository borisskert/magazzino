import {ChangeDetectorRef, Component, EventEmitter, Input, Output, ViewChild} from '@angular/core';
import {emptyPage, Page, toEmptyPage} from "../../pagination/page";
import {MatPaginator, MatPaginatorIntl, PageEvent} from "@angular/material/paginator";
import {Sort} from "@angular/material/sort";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {EditProductDialogComponent} from "../edit-product-dialog/edit-product-dialog.component";
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
    dialogConfig.width = '75vw';
    dialogConfig.height = '75vh';

    const matDialogRef = this.dialog.open(EditProductDialogComponent, {
      ...dialogConfig,
      data: product,
    });

    matDialogRef.componentInstance.save.subscribe((product: Product) => {
      this.edit.emit(product);
    });
  }
}
