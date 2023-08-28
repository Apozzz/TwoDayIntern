import { Injectable } from '@angular/core';
import { SortConfig } from '../shared/models/sort-config.interface';

@Injectable({
  providedIn: 'root'
})
export class SortingService<T> {

  sortData(data: T[], sortConfigs: SortConfig<T>[], sortOption: string): T[] {
    const currentConfig = sortConfigs.find(config => config.value === sortOption);

    if (currentConfig) {
      return [...data].sort(currentConfig.comparator);
    }

    return data;
  }

}
