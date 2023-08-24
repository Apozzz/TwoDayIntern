import { Injectable } from '@angular/core';
import { PurchaseDto } from './purchase.service';

@Injectable({
  providedIn: 'root'
})
export class LineGraphService {

  generateYearlyDataset(attribute: string, label: string, data: Record<number, PurchaseDto[]>): any {
    const sumByAttribute = (purchases: any[], attribute: string) => {
      return purchases.reduce((acc, purchase) => acc + purchase[attribute], 0);
    }

    const extractedData = [];

    for (let i = 1; i <= 12; i++) {
      if (data[i]) {
        extractedData.push(sumByAttribute(data[i], attribute));
      } else {
        extractedData.push(0);
      }
    }

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

  generateMonthlyDataset(attribute: string, label: string, data: Record<number, PurchaseDto[]>): any {
    const sumByAttribute = (purchases: any[], attribute: string) => {
      return purchases.reduce((acc, purchase) => acc + purchase[attribute], 0);
    }

    const extractedData = [];

    for (let i = 1; i <= 31; i++) {
      if (data[i]) {
        extractedData.push(sumByAttribute(data[i], attribute));
      } else {
        extractedData.push(0);
      }
    }

    console.log(extractedData);

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
