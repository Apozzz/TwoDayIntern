import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '@environments/environment';
import { Observable, map } from 'rxjs';
import { PurchaseDto } from '../shared/models/purchase-dto.interface';

@Injectable({
  providedIn: 'root'
})
export class PurchaseService {

  private baseUrl: string = `${environment.apiBaseUrl}/purchases`;

  constructor(private http: HttpClient) {}

  getYearlyPurchases(yearDate: string): Observable<Record<string, PurchaseDto[]>> {
    let params = new HttpParams()
      .set('dateTime', yearDate.toString());

    return this.http.get<PurchaseDto[]>(`${this.baseUrl}/year-range`, { params: params })
      .pipe(map(purchases => this.aggregateYearly(purchases))
      );
  }

  getMonthlyPurchases(monthDate: string): Observable<Record<string, PurchaseDto[]>> {
    let params = new HttpParams()
      .set('dateTime', monthDate.toString());

    return this.http.get<PurchaseDto[]>(`${this.baseUrl}/month-range`, { params: params })
      .pipe(map(purchases => this.aggregateMonthly(purchases))
      );
  }

  private aggregateMonthly(purchases: PurchaseDto[]): Record<string, PurchaseDto[]> {
    const aggregated: Record<string, PurchaseDto[]> = {};

    purchases.forEach(purchase => {
      const dayOfMonth = new Date(purchase.timeStamp).getDate();
      if (!aggregated[dayOfMonth]) aggregated[dayOfMonth] = [];
      aggregated[dayOfMonth].push(purchase);
    });

    return aggregated;
  }

  private aggregateYearly(purchases: PurchaseDto[]): Record<string, PurchaseDto[]> {
    const aggregated: Record<string, PurchaseDto[]> = {};

    purchases.forEach(purchase => {
      const monthOfYear = new Date(purchase.timeStamp).getMonth() + 1;

      if (!aggregated[monthOfYear]) aggregated[monthOfYear] = [];
      aggregated[monthOfYear].push(purchase);
    });

    return aggregated;
  }

}
export { PurchaseDto };

