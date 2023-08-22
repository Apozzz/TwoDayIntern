import { Component, OnInit } from '@angular/core';
import { MONTH_NAMES } from '@constants/month-names.constants';
import { TranslateService } from '@ngx-translate/core';
import { LineGraphService } from '@services/line-graph.service';
import { PurchaseService } from '@services/purchase.service';
import { ChartConfiguration } from 'chart.js';
import { ToastrService } from 'ngx-toastr';
import { GRAPHS_ATTRIBUTE_LABEL } from '@constants/graphs-attribute-label.constants';

@Component({
  selector: 'app-profit-graph',
  templateUrl: './profit-graph.component.html',
  styleUrls: ['./profit-graph.component.less']
})
export class ProfitGraphComponent implements OnInit {

  attributes: string[] = ['totalPrice', 'quantity'];
  lineChartType: any = 'line';
  lineChartLegend = true;
  lineChartPlugins = [];
  dataCache: any;
  lineChartData: { labels: string[], datasets: { data: number[], label: string, fill: boolean, tension: number, borderColor: string, backgroundColor: string }[] } = {
    labels: [],
    datasets: [
      {
        data: [],
        label: '',
        fill: false,
        tension: 0.5,
        borderColor: '',
        backgroundColor: ''
      }
    ]
  };
  lineChartLabels: string[] = [];
  lineChartOptions: ChartConfiguration['options'] = {
    responsive: true
  };

  constructor(private purchaseService: PurchaseService, private toastr: ToastrService, private translate: TranslateService, private lineGraphService: LineGraphService) { }

  ngOnInit(): void {
    this.translate.onLangChange.subscribe(() => {
      this.updateGraphLabels();
      this.setChartData(this.dataCache);
    });

    this.updateGraphLabels();
    this.populateChartData();
  }

  updateGraphLabels(): void {
    this.lineChartLabels = MONTH_NAMES.map(month => this.translate.instant(`MONTHS.${month}`));
  }

  populateChartData(): void {
    this.purchaseService.getMonthlyPurchases().subscribe({
      next: profits => {
        this.dataCache = profits;
        this.setChartData(profits);
      },
      error: error => {
        let errorMessage = error?.message || 'Something went wrong!';
        this.toastr.error(errorMessage, 'Error');
      }
    });
  }

  setChartData(profits: any): void {
    if (!profits) {
      return;
    }

    const datasets = GRAPHS_ATTRIBUTE_LABEL.map(pair => this.lineGraphService.generateDataset(pair.attribute, this.translate.instant(pair.label), profits));
    this.lineChartData = {
      labels: Object.keys(profits).map(monthNumber => this.lineChartLabels[+monthNumber - 1]),
      datasets: datasets,
    }
  }

}
