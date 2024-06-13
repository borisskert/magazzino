import {Component} from '@angular/core';
import {ProductDataSource} from "../service/product-data-source";
import {Observable} from "rxjs";
import {Page} from "../../pagination/page";
import {ProductSearch} from "../model/product-search";
import {Product} from "../../shopping-cart/model/product";
import {PageEvent} from "@angular/material/paginator";

@Component({
  selector: 'app-products-container',
  templateUrl: './products-container.component.html',
  styleUrl: './products-container.component.scss'
})
export class ProductsContainerComponent {

  constructor(private readonly dataSource: ProductDataSource) {
  }

  public get search$(): Observable<ProductSearch> {
    return this.dataSource.search$;
  }

  public get products$(): Observable<Page<Product>> {
    return this.dataSource.products$;
  }

  onPageChange($event: PageEvent) {
    this.dataSource.refresh({
        page: $event.pageIndex,
        size: $event.pageSize,
      }
    )
  }
}
