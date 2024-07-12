import {Component, EventEmitter, Inject, Output} from '@angular/core';
import {MAT_DIALOG_DATA} from "@angular/material/dialog";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Product} from "../model/product";

@Component({
  selector: 'app-edit-product-dialog',
  templateUrl: './edit-product-dialog.component.html',
  styleUrl: './edit-product-dialog.component.scss'
})
export class EditProductDialogComponent {
  title: string = '';

  form: FormGroup;

  @Output()
  save: EventEmitter<Product> = new EventEmitter<Product>();

  constructor(
    @Inject(MAT_DIALOG_DATA) public readonly data: { title: string, product: Product },
    private readonly formBuilder: FormBuilder
  ) {
    this.title = data.title;

    this.form = this.formBuilder.group({
      id: [data.product.id],
      number: [data.product.number, [Validators.required, Validators.minLength(3)]],
      name: [data.product.name, [Validators.required, Validators.minLength(3)]],
      description: [data.product.description, Validators.minLength(3)],
      price: [data.product.price, [Validators.required, Validators.min(0), Validators.pattern(/^\d+(\.\d{1,2})?$/)]],
    });
  }

  onSave() {
    if (this.form.valid) {
      this.save.emit(this.form.value);
    }
  }
}

export interface EditableProduct {
  id?: string;
  number?: string;
  name?: string;
  description?: string;
  price?: number;
}

export function newProduct(): EditableProduct {
  return {};
}
