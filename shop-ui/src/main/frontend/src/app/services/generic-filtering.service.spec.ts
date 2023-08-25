import { TestBed } from '@angular/core/testing';

import { GenericFilteringService } from './generic-filtering.service';

describe('GenericFilteringService', () => {
  let service: GenericFilteringService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GenericFilteringService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
