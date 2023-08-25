import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GenericSortComponent } from './generic-sort.component';

describe('GenericSortComponent', () => {
  let component: GenericSortComponent;
  let fixture: ComponentFixture<GenericSortComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [GenericSortComponent]
    });
    fixture = TestBed.createComponent(GenericSortComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
