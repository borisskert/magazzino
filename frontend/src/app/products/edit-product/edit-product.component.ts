import {Component, EventEmitter, Input, Output} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Product} from "../../shopping-cart/model/product";

@Component({
  selector: 'app-edit-product',
  templateUrl: './edit-product.component.html',
  styleUrl: './edit-product.component.scss'
})
export class EditProductComponent {
  form: FormGroup;

  @Input()
  set product(product: Product) {
    this.form.patchValue(product);
  }

  @Output()
  save: EventEmitter<Product> = new EventEmitter<Product>();

  constructor(private readonly formBuilder: FormBuilder) {
    this.form = this.formBuilder.group({
      number: [null, [Validators.required, Validators.minLength(3)]],
      name: [null, [Validators.required, Validators.minLength(3)]],
      description: [null, Validators.minLength(3)],
      price: [null, [Validators.required, Validators.min(0)]]
    });
  }

  onSubmit() {
    if (this.form.valid) {
      this.save.emit(this.form.value);
    }
  }
}
