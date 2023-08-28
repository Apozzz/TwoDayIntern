import { TestBed } from '@angular/core/testing';

import { LineGraphService } from './line-graph.service';

describe('LineGraphService', () => {
  let service: LineGraphService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LineGraphService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
