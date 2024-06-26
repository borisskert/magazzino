import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ProductFilterComponent} from './product-filter.component';
import {ProductsModule} from "../products-module";
import {NoopAnimationsModule} from "@angular/platform-browser/animations";

describe('ProductFilterComponent', () => {
  let component: ProductFilterComponent;
  let fixture: ComponentFixture<ProductFilterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        ProductsModule,
        NoopAnimationsModule,
      ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(ProductFilterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
