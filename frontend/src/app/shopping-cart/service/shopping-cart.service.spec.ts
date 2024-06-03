import {TestBed} from '@angular/core/testing';

import {ShoppingCartService} from './shopping-cart.service';
import {provideHttpClientTesting} from "@angular/common/http/testing";
import {provideHttpClient} from "@angular/common/http";

describe('ShoppingCartService', () => {
  let service: ShoppingCartService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        provideHttpClient(),
        provideHttpClientTesting(),
      ],
    });
    service = TestBed.inject(ShoppingCartService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
