import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { ProductDto } from '../shared/models/product-dto.interface';
import { FilterData } from '../shared/models/filter-data.interface';

@Injectable({
  providedIn: 'root'
})
export class ProductFilteringService {

  filterProducts(products: ProductDto[], filters: FilterData): Observable<ProductDto[]> {
    let filtered = products;

    if (filters.searchName) {
      filtered = filtered.filter(p => p.name.includes(filters.searchName));
    }

    filtered = filtered.filter(p => p.quantity >= filters.quantityRange[0] && p.quantity <= filters.quantityRange[1]);
    filtered = filtered.filter(p => p.price >= filters.priceRange[0] && p.price <= filters.priceRange[1]);
    return of(filtered);
  }

  getMinPrice(products: any[]): number {
    return Math.min(...products.map(product => product.price));
  }

  getMaxPrice(products: any[]): number {
    return Math.max(...products.map(product => product.price));
  }

  getMinQuantity(products: any[]): number {
    return Math.min(...products.map(product => product.quantity));
  }

  getMaxQuantity(products: any[]): number {
    return Math.max(...products.map(product => product.quantity));
  }

}
