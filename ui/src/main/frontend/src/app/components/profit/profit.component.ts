import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MAT_DATE_FORMATS } from '@angular/material/core';
import { MatDatepicker } from '@angular/material/datepicker';
import { ActivatedRoute, Router } from '@angular/router';
import { DATE_YEAR_MONTH_FORMAT } from '@constants/date-formats.constants';
import { GRAPH_TYPES } from '@constants/graph-types.constants';
import { GRAPHS_ATTRIBUTE_LABEL_MONTHLY, GRAPHS_ATTRIBUTE_LABEL_YEARLY } from '@constants/graphs-attribute-label.constants';
import { CustomTranslationService } from '@services/custom-translation.service';
import { ProfitService } from '@services/profit.service';
import { PurchaseDto } from '@services/purchase.service';
import * as moment from 'moment';
import { Subject, takeUntil } from 'rxjs';

@Component({
  selector: 'app-profit',
  templateUrl: './profit.component.html',
  styleUrls: ['./profit.component.less'],
  providers: [
    { provide: MAT_DATE_FORMATS, useValue: DATE_YEAR_MONTH_FORMAT },
  ],
})
export class ProfitComponent implements OnInit, OnDestroy {

  isYearlyViewMode: boolean = false;
  selectedDate = new FormControl(moment().startOf('year'));
  profitData: Record<string, PurchaseDto[]> = {};
  attributeLabel: any[] = [];
  selectedChartType: any = 'line';
  chartTypes: any[] = GRAPH_TYPES;
  private unsubscribe$ = new Subject<void>();

  constructor(private route: ActivatedRoute,
    private router: Router,
    private profitService: ProfitService,
    private customTranslationService: CustomTranslationService
  ) { }

  ngOnInit(): void {
    this.route.data
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe(data => {
        this.profitData = data['profitData'];
      });

    this.isYearlyViewMode = this.profitService.getSelectedGraphsViewMode();
    const savedDate = this.profitService.getSelectedDateByViewMode();
    this.setDateValue(savedDate);
    this.attributeLabel = this.customTranslationService.translateAttributes(this.isYearlyViewMode ? GRAPHS_ATTRIBUTE_LABEL_YEARLY : GRAPHS_ATTRIBUTE_LABEL_MONTHLY);
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  onToggleChange(event: any): void {
    this.isYearlyViewMode = event.checked;
    this.profitService.setSelectedGraphsViewMode(this.isYearlyViewMode);
    const selectedDateValue = this.selectedDate.value!;

    if (this.isYearlyViewMode) {
      this.router.navigate([`/graphs/${selectedDateValue.year()}`]);
      return;
    }

    this.router.navigate([`/graphs/${selectedDateValue.year()}/${selectedDateValue.month()}`]);
  }

  chosenYearHandler(selectedDate: moment.Moment, picker: MatDatepicker<moment.Moment>): void {
    if (this.isYearlyViewMode) {
      this.selectedDate.setValue(moment(selectedDate));
      const year = this.selectedDate.value!.year();
      this.profitService.setSelectedYearly(year.toString());
      this.router.navigate([`/graphs/${year}`]);
      picker.close();
    }
  }

  chosenMonthHandler(selectedDate: moment.Moment, picker: MatDatepicker<moment.Moment>): void {
    if (!this.isYearlyViewMode) {
      this.selectedDate.setValue(moment(selectedDate));
      const year = this.selectedDate.value!.year();
      const month = this.selectedDate.value!.month() + 1;
      this.profitService.setSelectedMonthly(selectedDate.toDate());
      this.router.navigate([`/graphs/${year}/${month}`]);
      picker.close();
    }
  }

  setDateValue(dateValue: string) {
    let updatedMoment;

    if (this.isYearlyViewMode) {
      updatedMoment = moment({ year: +dateValue, month: 0 });
    } else {
      const [year, month] = dateValue.split('-');
      updatedMoment = moment({ year: +year, month: +month - 1 });
    }

    this.selectedDate.setValue(updatedMoment);
  }

  getDataTableColumns(): string[] {
    const tableColumns = [];
    
    tableColumns.push(this.isYearlyViewMode ? 'month' : 'day');

    this.attributeLabel.forEach(element => {
      tableColumns.push(element.label);
    });

    return tableColumns;
  }

}
