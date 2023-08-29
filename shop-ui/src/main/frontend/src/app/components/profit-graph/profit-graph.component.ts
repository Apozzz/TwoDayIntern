import { Component, Input, OnChanges, OnDestroy, OnInit, SimpleChanges } from '@angular/core';
import { MONTH_NAMES } from '@constants/month-names.constants';
import { TranslateService } from '@ngx-translate/core';
import { GraphService } from '@services/graph.service';
import { ChartConfiguration } from 'chart.js';
import { Subscription } from 'rxjs';
import { AttributeConfig } from 'src/app/shared/models/attribute-config.interface';

const DAYS_IN_MONTH = 31;

@Component({
  selector: 'app-profit-graph',
  templateUrl: './profit-graph.component.html',
  styleUrls: ['./profit-graph.component.less'],
})
export class ProfitGraphComponent implements OnInit, OnDestroy, OnChanges {

  @Input() isYearlyViewMode: boolean = true;
  @Input() data: Record<string, any> = [];
  @Input() type: any = 'line';
  @Input() attributes: AttributeConfig[] = [];
  @Input() legend: boolean = true;
  @Input() plugins: any[] = [];
  @Input() options: ChartConfiguration['options'] = {
    responsive: true
  };
  
  displayData: ChartConfiguration['data'] = {
    labels: [],
    datasets: []
  };

  private subscription = new Subscription();

  constructor(
    private translate: TranslateService,
    private graphService: GraphService
  ) {}

  ngOnInit(): void {
    this.subscription.add(
      this.translate.onLangChange.subscribe(() => {
        this.updateGraphLabels();
      })
    );

    this.updateGraphLabels();
    this.populateChartData();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['chartData'] && !changes['chartData'].isFirstChange()) {
      this.populateChartData();
    }
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  updateGraphLabels(): void {
    this.displayData.labels = this.isYearlyViewMode 
      ? MONTH_NAMES.map(month => this.translate.instant(`MONTHS.${month}`))
      : Array.from({ length: DAYS_IN_MONTH }, (_, i) => (i + 1).toString());
  }

  populateChartData(): void {
    if (!this.data) {
      return;
    }

    const datasets = this.isYearlyViewMode ? 
      this.attributes.map(pair => this.graphService.generateYearlyDataset(pair.attribute, this.translate.instant(pair.label), this.data))
      : this.attributes.map(pair => this.graphService.generateMonthlyDataset(pair.attribute, this.translate.instant(pair.label), this.data));
    

    this.displayData = {
      ...this.displayData,
      datasets: datasets,
    };
  }

}