import { inject } from '@angular/core';
import { ResolveFn } from '@angular/router';
import { ProfitService } from '@services/profit.service';
import { PurchaseDto, PurchaseService } from '@services/purchase.service';
import { Observable } from 'rxjs';

export const profitResolver: ResolveFn<Observable<Record<string, PurchaseDto[]>>> = () => {
  const purchaseService = inject(PurchaseService);
  const profitService = inject(ProfitService)

  const viewMode = profitService.getSelectedGraphsViewMode();

  return viewMode ?
    purchaseService.getYearlyPurchases(profitService.getSelectedYearly())
    : purchaseService.getMonthlyPurchases(profitService.getSelectedMonthly());
};
