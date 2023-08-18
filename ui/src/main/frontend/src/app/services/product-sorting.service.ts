import { Injectable } from '@angular/core';
import { ProductDto } from './product.service';
import { Observable, of } from 'rxjs';

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
      case 'id-asc':
        sortedProducts.sort((a, b) => a.id - b.id);
        break;
      default:
        break;
    }

    return of(sortedProducts);
  }

}
