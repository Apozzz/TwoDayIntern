import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfitGraphComponent } from './profit-graph.component';

describe('ProfitGraphComponent', () => {
  let component: ProfitGraphComponent;
  let fixture: ComponentFixture<ProfitGraphComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProfitGraphComponent]
    });
    fixture = TestBed.createComponent(ProfitGraphComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
