import { TestBed } from '@angular/core/testing';

import { GenericSortingService } from './generic-sorting.service';

describe('GenericSortingService', () => {
  let service: GenericSortingService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GenericSortingService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
