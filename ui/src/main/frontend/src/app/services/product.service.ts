import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '@environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private baseURL: string = `${environment.apiBaseUrl}/warehouses/1/products`;

  constructor(private httpClient: HttpClient) {}

  getProducts(): Observable<ProductDto[]> {
    return this.httpClient.get<ProductDto[]>(this.baseURL);
  }
}

export interface ProductDto {
  id: number;
  name: string;
  description: string;
  price: number;
  finalPrice: number;
  quantity: number;
}