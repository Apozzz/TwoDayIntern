import { Component, OnInit } from '@angular/core';
import { PurchaseService } from '@services/purchase.service';
import { ChartConfiguration } from 'chart.js';
import { MONTH_NAMES } from '@constants/month-names.constants';

@Component({
  selector: 'app-profit-graph',
  templateUrl: './profit-graph.component.html',
  styleUrls: ['./profit-graph.component.less']
})
export class ProfitGraphComponent implements OnInit {

  public barChartLegend = true;
  public barChartPlugins = [];

  public barChartData: ChartConfiguration<'bar'>['data'] | undefined;
  public barChartOptions: ChartConfiguration<'bar'>['options'] = {
    responsive: true
  };

  constructor(private purchaseService: PurchaseService) { }

  ngOnInit(): void {
    this.purchaseService.getMonthlyTotalProfit().subscribe(profits => {
      this.barChartData = {
        labels: Object.keys(profits).map(monthNumber => MONTH_NAMES[+monthNumber - 1]),
        datasets: [
          {
            data: Object.values(profits),
            label: 'Monthly Profits'
          }
        ]
      };
    });
  }
}
