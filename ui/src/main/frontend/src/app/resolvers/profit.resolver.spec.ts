import { TestBed } from '@angular/core/testing';
import { ResolveFn } from '@angular/router';

import { profitResolver } from './profit.resolver';

describe('profitResolver', () => {
  const executeResolver: ResolveFn<boolean> = (...resolverParameters) => 
      TestBed.runInInjectionContext(() => profitResolver(...resolverParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeResolver).toBeTruthy();
  });
});
