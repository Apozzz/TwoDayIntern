import { Component, Input, OnInit, SimpleChanges, ViewChild, AfterViewInit, OnDestroy } from '@angular/core';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { TranslateService } from '@ngx-translate/core';
import { Subscription } from 'rxjs';
import { TableService } from '@services/table.service';
import { MONTH_NAMES } from '@constants/month-names.constants';

@Component({
  selector: 'app-profit-table',
  templateUrl: './profit-table.component.html',
  styleUrls: ['./profit-table.component.less']
})
export class ProfitTableComponent implements OnInit, AfterViewInit, OnDestroy {

  @Input() isYearlyViewMode: boolean = true;
  @Input() tableData: any;
  @Input() attributes: { attribute: string; label: string; }[] = [];
  @Input() tableColumns: string[] = [];
  dataSource = new MatTableDataSource<any>();

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  private subscriptions = new Subscription();

  constructor(
    private translate: TranslateService,
    private tableService: TableService,
  ) { }

  ngOnInit() {
    this.subscriptions.add(
      this.translate.onLangChange.subscribe(() => {
        this.populateChartData();
      })
    );

    this.populateChartData();
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['tableData'] && !changes['tableData'].isFirstChange()) {
      this.populateChartData();
    }
  }

  ngOnDestroy(): void {
    this.subscriptions.unsubscribe();
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  populateChartData(): void {
    if (!this.tableData) {
      return;
    }

    if (this.isYearlyViewMode) {
      const months: string[] = MONTH_NAMES.map(month => this.translate.instant(`MONTHS.${month}`));
      const data = this.tableService.generateYearlyTableData(months, this.attributes, this.tableData);
      this.dataSource.data = data;
      return;
    }

    const data = this.tableService.generateMonthlyTableData(this.attributes, this.tableData);
    this.dataSource.data = data;
  }
}
