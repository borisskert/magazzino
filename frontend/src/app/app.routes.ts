import {Routes} from '@angular/router';
import {
  ShoppingCartContainerComponent
} from "./shopping-cart/shopping-cart-container/shopping-cart-container.component";
import {ProductsContainerComponent} from "./products/products-container/products-container.component";

export const routes: Routes = [
  {path: '', component: ShoppingCartContainerComponent},
  {path: 'shopping-carts', component: ShoppingCartContainerComponent},
  {path: 'products', component: ProductsContainerComponent},
];
