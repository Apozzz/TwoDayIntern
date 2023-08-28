import { Injectable } from '@angular/core';
import { FilterCriterion } from '../shared/types/filter-criterion.type';

@Injectable({
  providedIn: 'root'
})
export class FilteringService {

  initializeCriteriaFromData<T>(data: T[], fields: Array<keyof T>): FilterCriterion<T>[] {
    return fields.map(field => {
      if (typeof data[0][field] === 'number') {
        return {
          field: field,
          minValue: 0,
          maxValue: this.getMaxValueFromData(data, field),
        };
      } else {
        return {
          field: field,
          value: '',
        };
      }
    });
  }

  filterData<T>(
    data: T[],
    criteria: FilterCriterion<T>[]
  ): T[] {
    return data.filter((item: T) => {
      return criteria.every((criterion: FilterCriterion<T>) => {
        if ('value' in criterion) {
          return criterion.value === '' ? true : String(item[criterion.field]).includes(String(criterion.value));
        } else if ('minValue' in criterion && 'maxValue' in criterion) {
          const itemValue = item[criterion.field] as unknown;

          if (typeof itemValue === 'number') {
            return (
              itemValue >= (criterion.minValue ?? -Infinity) &&
              itemValue <= (criterion.maxValue ?? Infinity)
            );
          }

          return true;
        }
        
        return true;
      });
    });
  }

  getMaxValueFromData<T, K extends keyof T>(data: T[], key: K): number {
    return Math.max(...data.map(item => item[key] as any));
  }

  getMinValueFromData<T>(data: T[], key: keyof T): number {
    return Math.min(...data.map(item => item[key] as any));
  }

}