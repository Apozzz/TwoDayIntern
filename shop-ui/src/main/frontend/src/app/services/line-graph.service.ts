import { Injectable } from '@angular/core';
import { PurchaseDto } from './purchase.service';
import { DataAggregationService } from './data-aggregation.service';

@Injectable({
  providedIn: 'root'
})
export class LineGraphService {

  constructor(private dataAggregationService: DataAggregationService) { }

  generateDataset(attribute: string, label: string, data: Record<number, PurchaseDto[]>, iterations: number): any {
    const timeUnits = Array.from({ length: iterations }, (_, i) => i + 1);
    const aggregatedData = this.dataAggregationService.generateAggregatedData<PurchaseDto>(timeUnits, [{ attribute, label }], data);
    const extractedData = aggregatedData.map(data => data[label]);
    const randomBorderColor = this.getRandomColor();
    const transparentBackgroundColor = this.getTransparentColor(randomBorderColor);

    return {
      data: extractedData,
      label: label,
      borderColor: randomBorderColor,
      backgroundColor: transparentBackgroundColor,
      fill: false,
      tension: 0.5,
    };
  }

  generateYearlyDataset(attribute: string, label: string, data: Record<number, PurchaseDto[]>): any {
    return this.generateDataset(attribute, label, data, 12);
  }

  generateMonthlyDataset(attribute: string, label: string, data: Record<number, PurchaseDto[]>): any {
    return this.generateDataset(attribute, label, data, 31);
  }

  getRandomColor(): string {
    const letters = '0123456789ABCDEF';
    let color = '#';

    for (let i = 0; i < 6; i++) {
      color += letters[Math.floor(Math.random() * 16)];
    }

    return color;
  }

  getTransparentColor(borderColor: string): string {
    return borderColor + '80';
  }

}
