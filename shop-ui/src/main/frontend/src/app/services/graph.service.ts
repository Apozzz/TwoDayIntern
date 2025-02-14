import { Injectable } from '@angular/core';
import { GraphDataset } from '../shared/models/graph-dataset.interface';
import { DataAggregationService } from './data-aggregation.service';
import { PurchaseDto } from './purchase.service';

@Injectable({
  providedIn: 'root'
})
export class GraphService {

  constructor(private dataAggregationService: DataAggregationService) { }

  generateDataset(attribute: string, label: string, data: Record<number, PurchaseDto[]>, iterations: number): GraphDataset {
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
