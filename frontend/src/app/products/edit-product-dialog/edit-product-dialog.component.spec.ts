import {ComponentFixture, TestBed} from '@angular/core/testing';

import {EditProductDialogComponent} from './edit-product-dialog.component';
import {ProductsModule} from "../products-module";
import {NoopAnimationsModule} from "@angular/platform-browser/animations";
import {MAT_DIALOG_DATA} from "@angular/material/dialog";

describe('EditProductDialogComponent', () => {
  let component: EditProductDialogComponent;
  let fixture: ComponentFixture<EditProductDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        ProductsModule,
        NoopAnimationsModule,
      ],
      providers: [
        {
          provide: MAT_DIALOG_DATA, useValue: {
            title: 'Create Product',
            product: {id: '1', number: '123', name: 'Product 1', description: 'Description', price: 123.45}
          }
        },
      ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(EditProductDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
