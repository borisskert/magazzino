import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ShoppingCartListItemComponent} from './shopping-cart-list-item.component';

describe('ShoppingCartListItemComponent', () => {
  let component: ShoppingCartListItemComponent;
  let fixture: ComponentFixture<ShoppingCartListItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ShoppingCartListItemComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(ShoppingCartListItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
