import { TestBed } from '@angular/core/testing';

import { ProductFilteringService } from './product-filtering.service';

describe('ProductFilterService', () => {
  let service: ProductFilteringService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProductFilteringService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
