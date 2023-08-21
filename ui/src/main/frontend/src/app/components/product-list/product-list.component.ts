import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ProductFilteringService } from '@services/product-filtering.service';
import { ProductSortingService } from '@services/product-sorting.service';
import { ProductDto, ProductService } from '@services/product.service';
import { Subject, takeUntil } from 'rxjs';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.less']
})
export class ProductListComponent implements OnInit, OnDestroy {

  products: ProductDto[] = [];
  filteredProducts: ProductDto[] = [];
  private unsubscribe$ = new Subject<void>();

  constructor(private productService: ProductService, private productFilteringService: ProductFilteringService, private productSortingService: ProductSortingService, private router: Router) { }

  ngOnInit(): void {
    this.fetchProducts();
  }

  fetchProducts(): void {
    this.productService.getProducts()
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe(data => {
        this.products = data;
        this.filteredProducts = [...this.products];
        this.sortProductsByDefault();
      });
  }

  onFilterChange(filters: { searchName: string, quantityRange: [number, number], priceRange: [number, number] }) {
    this.productFilteringService.filterProducts(this.products, filters)
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe(filteredProducts => {
        this.filteredProducts = filteredProducts;
      });
  }

  onSortChange(sortOption: string) {
    this.productSortingService.sortProducts(this.filteredProducts, sortOption)
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe(sortedProducts => {
        this.filteredProducts = sortedProducts;
      });
  }

  selectProductForPurchase(productId: number): void {
    this.router.navigate(['/purchases', productId]);
  }

  private sortProductsByDefault(): void {
    this.onSortChange('id-asc');
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

}
