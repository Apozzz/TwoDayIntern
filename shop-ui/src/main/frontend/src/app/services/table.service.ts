import { Injectable } from '@angular/core';
import { DataAggregationService } from './data-aggregation.service';
import { PurchaseDto } from './purchase.service';

@Injectable({
  providedIn: 'root'
})
export class TableService {

  constructor(private dataAggregationService: DataAggregationService) { }

  generateYearlyTableData(months: string[], attributes: { attribute: string; label: string; }[], data: Record<number, PurchaseDto[]>): any[] {
    const aggregatedData = this.dataAggregationService.generateAggregatedData<PurchaseDto>(months, attributes, data);
    return aggregatedData.map((data, index) => ({ MONTH: months[index], ...data }));
  }

  generateMonthlyTableData(attributes: { attribute: string; label: string; }[], data: Record<number, PurchaseDto[]>): any[] {
    const days = Array.from({ length: 31 }, (_, i) => i + 1);
    const aggregatedData = this.dataAggregationService.generateAggregatedData<PurchaseDto>(days, attributes, data);
    return aggregatedData.map((data, index) => ({ DAY: days[index], ...data }));
  }

}