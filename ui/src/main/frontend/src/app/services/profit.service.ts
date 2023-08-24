import { Injectable } from '@angular/core';
import { LocalStorageService } from './local-storage.service';
import { LOCAL_STORAGE_KEYS } from '@constants/local-storage-keys.constants';

@Injectable({
  providedIn: 'root'
})
export class ProfitService {

  constructor(private localStorageService: LocalStorageService) { }

  getSelectedYear(): number {
    let year: number | null =  this.localStorageService.get<number>(LOCAL_STORAGE_KEYS.SELECTED_PURCHASE_GRAPH_YEAR);

    if (!year) {
      year = new Date().getFullYear();
      this.localStorageService.set(LOCAL_STORAGE_KEYS.SELECTED_PURCHASE_GRAPH_YEAR, year);
    }

    return year;
  }

  setSelectedYear(yearDate: number): void {
    this.localStorageService.set(LOCAL_STORAGE_KEYS.SELECTED_PURCHASE_GRAPH_YEAR, yearDate);
  }

}
