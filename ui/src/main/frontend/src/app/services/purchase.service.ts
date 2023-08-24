import { HttpClient, HttpParams } from '@angular/common/http';
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

  getMonthlyPurchases(yearDate: number): Observable<Record<string, PurchaseDto[]>> {
    let params = new HttpParams()
      .set('dateTime', yearDate.toString());

    return this.http.get<PurchaseDto[]>(this.baseUrl, { params: params })
      .pipe(map(purchases => this.aggregateMonthly(purchases))
      );
  }

  private aggregateMonthly(purchases: PurchaseDto[]): Record<string, PurchaseDto[]> {
    const aggregated: Record<string, PurchaseDto[]> = {};

    purchases.forEach(purchase => {
      const month = new Date(purchase.timeStamp).getMonth() + 1;

      if (!aggregated[month]) aggregated[month] = [];
      aggregated[month].push(purchase);
    });

    return aggregated;
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
