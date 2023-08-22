import { Component, OnInit } from '@angular/core';
import { MONTH_NAMES } from '@constants/month-names.constants';
import { TranslateService } from '@ngx-translate/core';
import { PurchaseService } from '@services/purchase.service';
import { ChartConfiguration } from 'chart.js';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-profit-graph',
  templateUrl: './profit-graph.component.html',
  styleUrls: ['./profit-graph.component.less']
})
export class ProfitGraphComponent implements OnInit {

  barChartLegend = true;
  barChartPlugins = [];
  dataCache: any;
  barChartData: { labels: string[], datasets: { data: number[], label: string }[] } = {
    labels: [],
    datasets: [
      {
        data: [],
        label: '',
      }
    ]
  };
  barChartLabels: string[] = [];
  barChartOptions: ChartConfiguration['options'] = {
    responsive: true
  };

  constructor(private purchaseService: PurchaseService, private toastr: ToastrService, private translate: TranslateService) { }

  ngOnInit(): void {
    this.translate.onLangChange.subscribe(() => {
      this.updateGraphLabels();
      this.setChartData(this.dataCache);
    });

    this.updateGraphLabels();
    this.populateChartData();
  }

  updateGraphLabels(): void {
    this.barChartLabels = MONTH_NAMES.map(month => this.translate.instant(`MONTHS.${month}`));
  }

  populateChartData(): void {
    this.purchaseService.getMonthlyTotalProfit().subscribe({
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

    this.barChartData = {
      labels: Object.keys(profits).map(monthNumber => this.barChartLabels[+monthNumber - 1]),
      datasets: [
        {
          data: Object.values(profits),
          label: this.translate.instant('MONTHLY_PROFITS'),
        }
      ]
    };
  }

}
