import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '@environments/environment';
import { Observable } from 'rxjs';
import { ProductDto } from '../shared/models/productDto.interface';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private baseUrl: string = `${environment.apiBaseUrl}/warehouses/1/products`;

  constructor(private httpClient: HttpClient) {}

  getProducts(): Observable<ProductDto[]> {
    return this.httpClient.get<ProductDto[]>(this.baseUrl);
  }
  
  purchaseProduct(productId: number, quantity: number): Observable<any> {
    const purchaseUrl = `${this.baseUrl}/${productId}/purchase?quantity=${quantity}`;
    return this.httpClient.post(purchaseUrl, {});
  }

}