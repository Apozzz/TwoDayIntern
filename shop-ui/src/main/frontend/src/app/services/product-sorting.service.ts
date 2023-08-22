import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { ProductDto } from '../shared/models/product-dto.interface';

@Injectable({
  providedIn: 'root'
})
export class ProductSortingService {

  sortProducts(products: ProductDto[], sortOption: string): Observable<ProductDto[]> {
    const sortedProducts = [...products];
    switch (sortOption) {
      case 'price-asc':
        sortedProducts.sort((a, b) => a.price - b.price);
        break;
      case 'price-desc':
        sortedProducts.sort((a, b) => b.price - a.price);
        break;
      case 'quantity-asc':
        sortedProducts.sort((a, b) => a.quantity - b.quantity);
        break;
      case 'quantity-desc':
        sortedProducts.sort((a, b) => b.quantity - a.quantity);
        break;
      case 'name-asc':
        sortedProducts.sort((a, b) => a.name.localeCompare(b.name));
        break;
      default:
        break;
    }

    return of(sortedProducts);
  }

}
