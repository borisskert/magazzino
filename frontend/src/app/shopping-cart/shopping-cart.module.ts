import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ShoppingCartContainerComponent} from "./shopping-cart-container/shopping-cart-container.component";
import {ShoppingCartFilterComponent} from "./shopping-cart-filter/shopping-cart-filter.component";
import {ShoppingCartListComponent} from "./shopping-cart-list/shopping-cart-list.component";
import {
  MatCell,
  MatCellDef,
  MatColumnDef,
  MatHeaderCell,
  MatHeaderCellDef,
  MatHeaderRow,
  MatHeaderRowDef,
  MatRow,
  MatRowDef,
  MatTable,
} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {MatFormField, MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";


@NgModule({
  declarations: [
    ShoppingCartContainerComponent,
    ShoppingCartListComponent,
    ShoppingCartFilterComponent,
  ],
  imports: [
    CommonModule,

    MatFormFieldModule,
    MatInputModule,

    MatTable,
    MatHeaderCell,
    MatCell,
    MatColumnDef,
    MatCellDef,
    MatHeaderCellDef,
    MatPaginator,
    MatHeaderRow,
    MatRow,
    MatHeaderRowDef,
    MatRowDef,
    MatFormField,
  ]
})
export class ShoppingCartModule {
}
