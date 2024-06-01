import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShoppingCartFilterComponent } from './shopping-cart-filter.component';

describe('ShoppingCartFilterComponent', () => {
  let component: ShoppingCartFilterComponent;
  let fixture: ComponentFixture<ShoppingCartFilterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ShoppingCartFilterComponent]
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
