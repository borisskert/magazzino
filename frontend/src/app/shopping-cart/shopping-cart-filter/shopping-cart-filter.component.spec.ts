import {ComponentFixture, TestBed} from '@angular/core/testing';
import {ShoppingCartFilterComponent} from './shopping-cart-filter.component';
import {NoopAnimationsModule} from "@angular/platform-browser/animations";
import {ShoppingCartModule} from "../shopping-cart.module";

describe('ShoppingCartFilterComponent', () => {
  let component: ShoppingCartFilterComponent;
  let fixture: ComponentFixture<ShoppingCartFilterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        NoopAnimationsModule,
        ShoppingCartModule
      ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(ShoppingCartFilterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
