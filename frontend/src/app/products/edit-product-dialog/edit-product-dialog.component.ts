import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA} from "@angular/material/dialog";
import {Product} from "../../shopping-cart/model/product";

@Component({
  selector: 'app-edit-product-dialog',
  templateUrl: './edit-product-dialog.component.html',
  styleUrl: './edit-product-dialog.component.scss'
})
export class EditProductDialogComponent {
  constructor(@Inject(MAT_DIALOG_DATA) public data: Product) {
  }

  onSave() {
    console.log('Save product', this.data);
  }
}
