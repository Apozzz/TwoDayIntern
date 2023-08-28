import { inject } from '@angular/core';
import { ResolveFn } from '@angular/router';
import { ProductService } from '@services/product.service';
import { Observable } from 'rxjs';
import { ProductDto } from '../shared/models/product-dto.interface';

export const productResolver: ResolveFn<Observable<ProductDto[]>> = () => {
  const productService: ProductService = inject(ProductService);
  return productService.getProducts();
};