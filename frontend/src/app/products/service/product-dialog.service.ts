import {Injectable} from '@angular/core';
import {MatDialog, MatDialogConfig, MatDialogRef} from "@angular/material/dialog";
import {EditProductDialogComponent, newProduct} from "../edit-product-dialog/edit-product-dialog.component";
import {Product} from "../model/product";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ProductDialogService {

  private dialogRef: MatDialogRef<EditProductDialogComponent> | undefined;

  constructor(private readonly dialog: MatDialog) {
  }

  public openNewProductDialog(): Observable<Product> {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.width = '75vw';
    dialogConfig.height = '75vh';

    return new Observable<Product>(
      observer => {
        this.dialogRef = this.dialog.open(EditProductDialogComponent, {
          ...dialogConfig,
          data: {
            title: 'Create Product',
            product: newProduct()
          },
        });

        this.dialogRef.componentInstance.save.subscribe((product: Product) => {
          observer.next(product);
        });
      }
    )
  }

  public openEditProductDialog(product: Product): Observable<Product> {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.width = '75vw';
    dialogConfig.height = '75vh';

    return new Observable<Product>(
      observer => {
        this.dialogRef = this.dialog.open(EditProductDialogComponent, {
          ...dialogConfig,
          data: {
            title: 'Edit Product',
            product
          },
        });

        this.dialogRef.componentInstance.save.subscribe((product: Product) => {
          observer.next(product);
        });
      }
    )
  }

  public closeDialog() {
    this.dialogRef?.close();
    this.dialogRef = undefined;
  }
}
