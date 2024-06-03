import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ShoppingCartContainerComponent} from './shopping-cart-container.component';
import {ShoppingCartModule} from "../shopping-cart.module";
import {provideHttpClient} from "@angular/common/http";
import {provideHttpClientTesting} from "@angular/common/http/testing";
import {NoopAnimationsModule} from "@angular/platform-browser/animations";

describe('ShoppingCartContainerComponent', () => {
  let component: ShoppingCartContainerComponent;
  let fixture: ComponentFixture<ShoppingCartContainerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      providers: [
        provideHttpClient(),
        provideHttpClientTesting(),
      ],
      imports: [
        NoopAnimationsModule,
        ShoppingCartModule
      ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(ShoppingCartContainerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
