import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ShoppingCartListComponent} from './shopping-cart-list.component';
import {NoopAnimationsModule} from "@angular/platform-browser/animations";
import {ShoppingCartModule} from "../shopping-cart.module";

describe('ShoppingCartListComponent', () => {
  let component: ShoppingCartListComponent;
  let fixture: ComponentFixture<ShoppingCartListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        NoopAnimationsModule,
        ShoppingCartModule
      ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(ShoppingCartListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
