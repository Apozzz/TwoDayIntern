import { Injectable } from '@angular/core';
import { LOCAL_STORAGE_KEYS } from '@constants/local-storage-keys.constants';
import { LocalStorageService } from './local-storage.service';

@Injectable({
  providedIn: 'root'
})
export class ProfitService {

  constructor(private localStorageService: LocalStorageService) { }

  getSelectedDateByViewMode(): string {
    const viewMode = this.getSelectedGraphsViewMode();
    return viewMode ? this.getSelectedYearly() : this.getSelectedMonthly();
  }

  getSelectedYearly(): string {
    let yearly: string | null = this.localStorageService.get<string>(LOCAL_STORAGE_KEYS.SELECTED_PURCHASE_GRAPH_YEARLY);

    if (!yearly) {
      yearly = new Date().getFullYear().toString();
      this.localStorageService.set(LOCAL_STORAGE_KEYS.SELECTED_PURCHASE_GRAPH_YEARLY, yearly);
    }

    return yearly;
  }

  setSelectedYearly(yearlyDate: string): void {
    this.localStorageService.set(LOCAL_STORAGE_KEYS.SELECTED_PURCHASE_GRAPH_YEARLY, yearlyDate);
  }

  getSelectedMonthly(): string {
    let monthly: string | null = this.localStorageService.get<string>(LOCAL_STORAGE_KEYS.SELECTED_PURCHASE_GRAPH_MONTHLY);

    if (!monthly) {
      const date = new Date();
      monthly = `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, '0')}`;
      this.localStorageService.set(LOCAL_STORAGE_KEYS.SELECTED_PURCHASE_GRAPH_MONTHLY, monthly);
    }

    return monthly;
  }

  setSelectedMonthly(selectedDate: Date): void {
    const monthlyDate = `${selectedDate.getFullYear()}-${String(selectedDate.getMonth() + 1).padStart(2, '0')}`;
    this.localStorageService.set(LOCAL_STORAGE_KEYS.SELECTED_PURCHASE_GRAPH_MONTHLY, monthlyDate);
  }

  getSelectedGraphsViewMode(): boolean {
    const viewMode: boolean | null = this.localStorageService.get<boolean>(LOCAL_STORAGE_KEYS.SELECTED_PURCHASE_VIEW_MODE);
    return viewMode ?? true;
  }

  setSelectedGraphsViewMode(viewMode: boolean): void {
    this.localStorageService.set(LOCAL_STORAGE_KEYS.SELECTED_PURCHASE_VIEW_MODE, viewMode);
  }

}
