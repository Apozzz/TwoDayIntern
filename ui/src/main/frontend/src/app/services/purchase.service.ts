import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '@environments/environment';
import { Observable, map } from 'rxjs';
import { ProductDto } from './product.service';

@Injectable({
  providedIn: 'root'
})
export class PurchaseService {

  private baseUrl: string = `${environment.apiBaseUrl}/purchases/current-year-range`;

  constructor(private http: HttpClient) { }

  getMonthlyTotalProfit(): Observable<Record<string, number>> {
    return this.http.get<PurchaseDto[]>(this.baseUrl).pipe(
      map(purchases => this.aggregateMonthlyTotal(purchases))
    );
  }

  private aggregateMonthlyTotal(purchases: PurchaseDto[]): Record<string, number> {
    const totals: Record<string, number> = {};

    purchases.forEach(purchase => {
      const month = new Date(purchase.timeStamp).getMonth() + 1;

      if (!totals[month]) totals[month] = 0;
      totals[month] += purchase.totalPrice;
    });

    return totals;
  }

}

export interface PurchaseDto {
  id: number;
  user: UserDto;
  product: ProductDto;
  quantity: number;
  totalPrice: number;
  timeStamp: string;
}

export interface UserDto {
  id: number;
  username: string;
  password: string;
}
