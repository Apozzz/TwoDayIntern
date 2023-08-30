import { Injectable } from '@angular/core';
import { AttributeConfig } from '../shared/models/attribute-config.interface';

@Injectable({
  providedIn: 'root'
})
export class DataAggregationService {

  generateAggregatedData<T>(timeUnits: string[] | number[], attributes: AttributeConfig[], data: Record<number, T[]>): any[] {
    const aggregatedData = [];

    for (let i = 0; i < timeUnits.length; i++) {
      const unitData: Record<string, any> = {};
      attributes.forEach(attr => {
        unitData[attr.label] = data[i + 1] ? this.sumByAttribute(data[i + 1], attr.attribute) : 0;
      });
      aggregatedData.push(unitData);
    }
    return aggregatedData;
  }

  private sumByAttribute<T>(items: T[], attribute: string): number {
    return items.reduce((acc, item) => acc + Number((item as any)[attribute]), 0);
  }

}