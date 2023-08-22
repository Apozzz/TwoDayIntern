import { TestBed } from '@angular/core/testing';

import { ProductSortingService } from './product-sorting.service';

describe('ProductSortingService', () => {
  let service: ProductSortingService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProductSortingService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
