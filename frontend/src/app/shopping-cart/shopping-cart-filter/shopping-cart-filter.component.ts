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

  onChangeId($event: KeyboardEvent) {
    if (this.form.invalid) {
      return;
    }

    const value = ($event.target as HTMLInputElement).value;
    this.search.id = value ? Number.parseInt(value, 10) : undefined;

    this.searchChange.emit(this.search);
  }

  onMinTotalPrice($event: KeyboardEvent) {
    if (this.form.invalid) {
      return;
    }

    const value = ($event.target as HTMLInputElement).value;
    this.search.minTotalPrice = value ? Number.parseFloat(value) : undefined;

    this.searchChange.emit(this.search);
  }

  onChangeProductName($event: KeyboardEvent) {
    if (this.form.invalid) {
      return;
    }

    const value = ($event.target as HTMLInputElement).value;
    this.search.productName = value ? value : undefined;

    this.searchChange.emit(this.search);
  }

  onChangeProductNumber($event: KeyboardEvent) {
    if (this.form.invalid) {
      return;
    }

    const value = ($event.target as HTMLInputElement).value;
    this.search.productNumber = value ? value : undefined;

    this.searchChange.emit(this.search);
  }
}
