import {Component} from '@angular/core';
import {ProductDataSource} from "../service/product-data-source";

@Component({
  selector: 'app-products-container',
  standalone: true,
  imports: [],
  templateUrl: './products-container.component.html',
  styleUrl: './products-container.component.scss'
})
export class ProductsContainerComponent {

  constructor(private readonly dataSource: ProductDataSource) {
  }
}
