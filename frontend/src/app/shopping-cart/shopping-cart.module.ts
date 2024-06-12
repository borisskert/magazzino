import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ShoppingCartContainerComponent} from "./shopping-cart-container/shopping-cart-container.component";
import {ShoppingCartFilterComponent} from "./shopping-cart-filter/shopping-cart-filter.component";
import {ShoppingCartListComponent} from "./shopping-cart-list/shopping-cart-list.component";
import {MatTableModule,} from "@angular/material/table";
import {MatPaginatorModule} from "@angular/material/paginator";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {ReactiveFormsModule} from "@angular/forms";

@NgModule({
  declarations: [
    ShoppingCartContainerComponent,
    ShoppingCartListComponent,
    ShoppingCartFilterComponent,
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,

    MatFormFieldModule,
    MatInputModule,
    MatTableModule,
    MatPaginatorModule,
  ]
})
export class ShoppingCartModule {
}
