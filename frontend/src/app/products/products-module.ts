import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {ReactiveFormsModule} from "@angular/forms";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatTableModule} from "@angular/material/table";
import {MatPaginatorModule} from "@angular/material/paginator";
import {ProductsContainerComponent} from "./products-container/products-container.component";
import {ProductListComponent} from "./product-list/product-list.component";
import {ProductFilterComponent} from "./product-filter/product-filter.component";
import {MatSortModule} from "@angular/material/sort";
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {EditProductDialogComponent} from "./edit-product-dialog/edit-product-dialog.component";
import {MatDialogModule} from "@angular/material/dialog";
import {NewProductComponent} from "./new-product/new-product.component";

@NgModule({
  declarations: [
    ProductsContainerComponent,
    ProductListComponent,
    ProductFilterComponent,
    EditProductDialogComponent,
    NewProductComponent,
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,

    MatFormFieldModule,
    MatInputModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatIconModule,
    MatDialogModule,
    MatButtonModule,
  ]
})
export class ProductsModule {
}
