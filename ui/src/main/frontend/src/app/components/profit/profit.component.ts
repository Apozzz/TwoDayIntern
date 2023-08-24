import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MAT_DATE_FORMATS } from '@angular/material/core';
import { MatDatepicker } from '@angular/material/datepicker';
import { ActivatedRoute, Router } from '@angular/router';
import { DATE_YEAR_FORMAT } from '@constants/date-formats.constants';
import { GRAPHS_ATTRIBUTE_LABEL } from '@constants/graphs-attribute-label.constants';
import { ProfitService } from '@services/profit.service';
import { PurchaseDto } from '@services/purchase.service';
import * as moment from 'moment';
import { Moment } from 'moment';
import { Subject, takeUntil } from 'rxjs';

@Component({
  selector: 'app-profit',
  templateUrl: './profit.component.html',
  styleUrls: ['./profit.component.less'],
  providers: [
    { provide: MAT_DATE_FORMATS, useValue: DATE_YEAR_FORMAT },
  ],
})
export class ProfitComponent implements OnInit, OnDestroy {

  date = new FormControl(moment().startOf('year'));
  profitData: Record<string, PurchaseDto[]> = {};
  attributeLabel = GRAPHS_ATTRIBUTE_LABEL;
  private unsubscribe$ = new Subject<void>();

  constructor(private route: ActivatedRoute, private router: Router, private profitService: ProfitService) {}

  ngOnInit(): void {
    this.route.data
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe(data => {
        this.profitData = data['profitData'];
      });

    const savedYear = this.profitService.getSelectedYear();
    this.profitData = this.route.snapshot.data['profitData'];

    if (!savedYear) {
      return;
    }

    this.setDateYearValue(+savedYear);
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  chosenYearHandler(selectedDate: Moment, datepicker: MatDatepicker<Moment>) {
    const dateYear = selectedDate.year();
    this.setDateYearValue(dateYear);
    this.profitService.setSelectedYear(dateYear);
    datepicker.close();
    this.router.navigate([`/graphs/${dateYear}`]);
  }

  setDateYearValue(yearDate: number) {
    const ctrlValue = this.date.value!;
    ctrlValue.year(yearDate);
    this.date.setValue(ctrlValue);
  }

}
