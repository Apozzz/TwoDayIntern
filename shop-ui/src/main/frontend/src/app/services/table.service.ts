import { Injectable } from '@angular/core';
import { PurchaseDto } from './purchase.service';

@Injectable({
  providedIn: 'root'
})
export class TableService {

  constructor() { }

  generateYearlyTableData(months: string[], attributes: { attribute: string; label: string; }[], data: Record<number, PurchaseDto[]>): any[] {
    const sumByAttribute = (purchases: any[], attribute: string) => {
      return purchases.reduce((acc, purchase) => acc + purchase[attribute], 0);
    };
  
    const tableData = [];
  
    for (let i = 1; i <= 12; i++) {
      const monthData: any = {
        month: months[i - 1]
      };
  
      attributes.forEach(attr => {
        monthData[attr.label] = data[i] ? sumByAttribute(data[i], attr.attribute) : 0;
      });
  
      tableData.push(monthData);
    }
  
    return tableData;
  }

  generateMonthlyTableData(attributes: { attribute: string; label: string; }[], data: Record<number, PurchaseDto[]>): any[] {
    const sumByAttribute = (purchases: any[], attribute: string) => {
      return purchases.reduce((acc, purchase) => acc + purchase[attribute], 0);
    };
  
    const tableData = [];
  
    for (let i = 1; i <= 31; i++) {
      const dayData: any = {
        day: i - 1
      };
  
      attributes.forEach(attr => {
        dayData[attr.label] = data[i] ? sumByAttribute(data[i], attr.attribute) : 0;
      });
  
      tableData.push(dayData);
    }
  
    return tableData;
  }
  
}
