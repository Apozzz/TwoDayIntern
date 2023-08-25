import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GenericFilterComponent } from './generic-filter.component';

describe('GenericFilterComponent', () => {
  let component: GenericFilterComponent;
  let fixture: ComponentFixture<GenericFilterComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [GenericFilterComponent]
    });
    fixture = TestBed.createComponent(GenericFilterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
