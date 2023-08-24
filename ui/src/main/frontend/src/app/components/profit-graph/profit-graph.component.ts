import { Component, Input, OnChanges, OnDestroy, OnInit, SimpleChanges } from '@angular/core';
import { MONTH_NAMES } from '@constants/month-names.constants';
import { TranslateService } from '@ngx-translate/core';
import { LineGraphService } from '@services/line-graph.service';
import { ChartConfiguration } from 'chart.js';
import { Subscription } from 'rxjs';


@Component({
  selector: 'app-profit-graph',
  templateUrl: './profit-graph.component.html',
  styleUrls: ['./profit-graph.component.less'],
})
export class ProfitGraphComponent implements OnInit, OnDestroy, OnChanges {

  @Input() chartData: any;
  @Input() chartType: any = 'line';
  @Input() attributes: { attribute: string; label: string; }[] = [];
  @Input() chartLegend: boolean = true;
  @Input() chartPlugins: any[] = [];
  @Input() chartOptions: ChartConfiguration['options'] = {
    responsive: true
  };
  
  chartDisplayData: ChartConfiguration['data'] = {
    labels: [],
    datasets: []
  };

  private subscriptions = new Subscription();

  constructor(
    private translate: TranslateService,
    private lineGraphService: LineGraphService
  ) { }

  ngOnInit(): void {
    this.subscriptions.add(
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
    this.subscriptions.unsubscribe();
  }

  updateGraphLabels(): void {
    this.chartDisplayData.labels = MONTH_NAMES.map(month => this.translate.instant(`MONTHS.${month}`));
  }

  populateChartData(): void {
    if (!this.chartData) {
      return;
    }

    const datasets = this.attributes.map(pair => this.lineGraphService.generateDataset(pair.attribute, this.translate.instant(pair.label), this.chartData));

    this.chartDisplayData = {
      ...this.chartDisplayData,
      datasets: datasets,
    };
  }

}