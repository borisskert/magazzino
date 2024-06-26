import {Component, EventEmitter, Input, Output} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {defaultProductSearch, ProductSearch} from "../model/product-search";

@Component({
  selector: 'app-product-filter',
  templateUrl: './product-filter.component.html',
  styleUrl: './product-filter.component.scss'
})
export class ProductFilterComponent {
  form: FormGroup;

  @Input({
    transform: (value: ProductSearch | null) => value || defaultProductSearch()
  })
  search: ProductSearch = defaultProductSearch();

  @Output()
  searchChange: EventEmitter<ProductSearch> = new EventEmitter<ProductSearch>();

  constructor(private readonly formBuilder: FormBuilder) {
    this.form = this.formBuilder.group({
      name: [this.search?.name],
      number: [this.search?.number],
      description: [this.search?.description],
    });
  }

  onChange() {
    let formValue = this.form.value;

    const search: ProductSearch = {
      ...this.search,
      name: formValue.name ? formValue.name : undefined,
      number: formValue.number ? formValue.number : undefined,
      description: formValue.description ? formValue.description : undefined,
    };

    this.searchChange.emit(search);
  }
}
