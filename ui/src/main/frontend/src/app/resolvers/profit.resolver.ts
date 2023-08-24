import { inject } from '@angular/core';
import { ResolveFn } from '@angular/router';
import { ProfitService } from '@services/profit.service';
import { PurchaseDto, PurchaseService } from '@services/purchase.service';
import { Observable } from 'rxjs';

export const profitResolver: ResolveFn<Observable<Record<string, PurchaseDto[]>>> = (route) => {
  const purchaseService = inject(PurchaseService);
  const profitService = inject(ProfitService)
  let year: number = profitService.getSelectedYear();
  
  if (!year) {
    year = new Date().getFullYear();
  }

  return purchaseService.getMonthlyPurchases(year);
};
