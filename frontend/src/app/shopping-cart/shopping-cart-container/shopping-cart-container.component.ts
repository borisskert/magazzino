import {Component, OnInit} from '@angular/core';
import {Observable} from "rxjs";
import {ShoppingCart} from "../model/shopping-cart";
import {ShoppingCartSearch} from "../model/shopping-cart-search";
import {ShoppingCartDataSource} from "../service/shopping-cart-data-source";
import {Page} from "../../pagination/page";
import {PageEvent} from "@angular/material/paginator";

@Component({
  selector: 'app-shopping-cart-container',
  templateUrl: './shopping-cart-container.component.html',
  styleUrl: './shopping-cart-container.component.scss'
})
export class ShoppingCartContainerComponent implements OnInit {

  constructor(private readonly shoppingCartDataSource: ShoppingCartDataSource) {
  }

  ngOnInit(): void {
    this.shoppingCartDataSource.refresh();
  }

  public get search$(): Observable<ShoppingCartSearch> {
    return this.shoppingCartDataSource.search$;
  }

  public get shoppingCarts$(): Observable<Page<ShoppingCart>> {
    return this.shoppingCartDataSource.shoppingCarts$;
  }

  onPageChange($event: PageEvent) {
    this.shoppingCartDataSource.refresh({
        page: $event.pageIndex,
        size: $event.pageSize,
      }
    )
  }

  onSearchChange($event: ShoppingCartSearch) {
    this.shoppingCartDataSource.refresh($event);
  }
}
