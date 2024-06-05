import {Component, EventEmitter, Input, Output} from '@angular/core';
import {
  defaultShoppingCartSearch,
  ShoppingCartSearch,
  toDefaultShoppingCartSearch
} from "../model/shopping-cart-search";

@Component({
  selector: 'app-shopping-cart-filter',
  templateUrl: './shopping-cart-filter.component.html',
  styleUrl: './shopping-cart-filter.component.scss'
})
export class ShoppingCartFilterComponent {

  @Input({
    transform: toDefaultShoppingCartSearch
  })
  public search: ShoppingCartSearch = defaultShoppingCartSearch()

  @Output()
  public searchChange: EventEmitter<ShoppingCartSearch> = new EventEmitter<ShoppingCartSearch>();

  onChangeId($event: KeyboardEvent) {
    const value = ($event.target as HTMLInputElement).value;
    this.search.id = value ? Number.parseInt(value, 10) : undefined;

    this.searchChange.emit(this.search);
  }

  onMinTotalPrice($event: KeyboardEvent) {
    const value = ($event.target as HTMLInputElement).value;
    this.search.minTotalPrice = value ? Number.parseFloat(value) : undefined;

    this.searchChange.emit(this.search);
  }

  onChangeProductName($event: KeyboardEvent) {
    const value = ($event.target as HTMLInputElement).value;
    this.search.productName = value ? value : undefined;

    this.searchChange.emit(this.search);
  }

  onChangeProductNumber($event: KeyboardEvent) {
    const value = ($event.target as HTMLInputElement).value;
    this.search.productNumber = value ? value : undefined;

    this.searchChange.emit(this.search);
  }
}
