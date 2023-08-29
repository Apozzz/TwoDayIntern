import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MAT_DATE_FORMATS } from '@angular/material/core';
import { MatDatepicker } from '@angular/material/datepicker';
import { ActivatedRoute, Router } from '@angular/router';
import { DATE_YEAR_MONTH_FORMAT } from '@constants/date-formats.constants';
import { GRAPH_TYPES } from '@constants/graph-types.constants';
import { GRAPHS_ATTRIBUTE_LABEL_MONTHLY, GRAPHS_ATTRIBUTE_LABEL_YEARLY } from '@constants/graphs-attribute-label.constants';
import { ProfitService } from '@services/profit.service';
import { PurchaseDto } from '@services/purchase.service';
import * as moment from 'moment';
import { Moment } from 'moment';
import { Subscription } from 'rxjs';
import { AttributeConfig } from 'src/app/shared/models/attribute-config.interface';

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
  attributeLabel: AttributeConfig[] = [];
  selectedChartType: string = 'line';
  chartTypes: any[] = GRAPH_TYPES;
  private subscription = new Subscription();

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private profitService: ProfitService,
  ) { }

  ngOnInit(): void {
    this.subscription.add(
      this.route.data
        .subscribe(data => {
          this.profitData = data['profitData'];
        }));

    this.isYearlyViewMode = this.profitService.getSelectedGraphsViewMode();
    this.setDateValue(this.profitService.getSelectedDateByViewMode());
    this.attributeLabel = this.isYearlyViewMode ? GRAPHS_ATTRIBUTE_LABEL_YEARLY : GRAPHS_ATTRIBUTE_LABEL_MONTHLY;
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  navigateToGraph(date: Moment): void {
    const year = date.year();
    const month = date.month() + 1;
    const url = this.isYearlyViewMode
      ? `/graphs/${year}`
      : `/graphs/${year}/${month}`;
    this.router.navigate([url]);
  }

  onToggleChange(event: any): void {
    this.isYearlyViewMode = event.checked;
    this.profitService.setSelectedGraphsViewMode(this.isYearlyViewMode);
    this.navigateToGraph(this.selectedDate.value!);
  }

  handleYear(selectedDate: Moment, picker: MatDatepicker<Moment>): void {
    if (this.isYearlyViewMode) {
      this.selectedDate.setValue(moment(selectedDate));
      this.profitService.setSelectedYearly(this.selectedDate.value!.year().toString());
      this.navigateToGraph(this.selectedDate.value!);
      picker.close();
    }
  }

  handleMonth(selectedDate: Moment, picker: MatDatepicker<Moment>): void {
    if (!this.isYearlyViewMode) {
      this.selectedDate.setValue(moment(selectedDate));
      this.profitService.setSelectedMonthly(selectedDate.toDate());
      this.navigateToGraph(this.selectedDate.value!);
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
    tableColumns.push(this.isYearlyViewMode ? 'MONTH' : 'DAY');
    this.attributeLabel.forEach(element => {
      tableColumns.push(element.label);
    });
    return tableColumns;
  }

}
