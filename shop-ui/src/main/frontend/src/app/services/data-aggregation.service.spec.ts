import { TestBed } from '@angular/core/testing';

import { DataAggregationService } from './data-aggregation.service';

describe('DataAggregationService', () => {
  let service: DataAggregationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DataAggregationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
