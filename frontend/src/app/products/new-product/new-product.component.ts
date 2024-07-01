import {Component, EventEmitter, Output} from '@angular/core';
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {EditProductDialogComponent, newProduct} from "../edit-product-dialog/edit-product-dialog.component";
import {Product} from "../model/product";

@Component({
  selector: 'app-new-product',
  templateUrl: './new-product.component.html',
  styleUrl: './new-product.component.scss'
})
export class NewProductComponent {

  @Output()
  create: EventEmitter<Product> = new EventEmitter<Product>();

  constructor(private readonly dialog: MatDialog) {
  }

  onNew() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.width = '75vw';
    dialogConfig.height = '75vh';

    const matDialogRef = this.dialog.open(EditProductDialogComponent, {
      ...dialogConfig,
      data: newProduct(),
    });

    matDialogRef.componentInstance.save.subscribe((product: Product) => {
      this.create.emit(product);
    });
  }
}
