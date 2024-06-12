import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {
  defaultShoppingCartSearch,
  ShoppingCartSearch,
  toDefaultShoppingCartSearch
} from "../model/shopping-cart-search";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-shopping-cart-filter',
  templateUrl: './shopping-cart-filter.component.html',
  styleUrl: './shopping-cart-filter.component.scss'
})
export class ShoppingCartFilterComponent implements OnInit {

  @Input({
    transform: toDefaultShoppingCartSearch
  })
  public search: ShoppingCartSearch = defaultShoppingCartSearch()

  @Output()
  public searchChange: EventEmitter<ShoppingCartSearch> = new EventEmitter<ShoppingCartSearch>();

  form: FormGroup = new FormGroup({});

  constructor(private readonly formBuilder: FormBuilder) {
  }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      id: [this.search.id, Validators.pattern('^[0-9]*$')],
      minTotalPrice: [this.search.minTotalPrice, Validators.pattern('^[0-9]*(\\.[0-9]*)?$')],
      productName: [this.search.productName],
      productNumber: [this.search.productNumber]
    });
  }

  onChange() {
    if (this.form.invalid) {
      return;
    }

    let formValue = this.form.value;

    this.search.id = formValue.id ? Number.parseInt(formValue.id, 10) : undefined;
    this.search.productName = formValue.productName ? formValue.productName : undefined;
    this.search.productNumber = formValue.productNumber ? formValue.productNumber : undefined;
    this.search.minTotalPrice = formValue.minTotalPrice ? Number.parseFloat(formValue.minTotalPrice) : undefined;

    this.searchChange.emit(this.search);
  }
}
