import { Injectable } from '@angular/core';
import { PurchaseDto } from './purchase.service';

@Injectable({
  providedIn: 'root'
})
export class LineGraphService {

  generateDataset(attribute: string, label: string, data: Record<number, PurchaseDto[]>): any {
    const sumByAttribute = (purchases: any[], attribute: string) => {
      return purchases.reduce((acc, purchase) => acc + purchase[attribute], 0);
    }

    const extractedData = Object.values(data).map(purchases => sumByAttribute(purchases, attribute));
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
