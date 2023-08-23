import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ProductFilteringService } from '@services/product-filtering.service';
import { ProductSortingService } from '@services/product-sorting.service';
import { ProductDto, ProductService } from '@services/product.service';
import { ToastrService } from 'ngx-toastr';
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
  currentSortOption: string = 'id-asc';

  constructor(private productService: ProductService, private productFilteringService: ProductFilteringService, private productSortingService: ProductSortingService, private router: Router, private toastr: ToastrService) { }

  ngOnInit(): void {
    this.fetchProducts();
  }

  fetchProducts(): void {
    this.productService.getProducts()
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe({
        next: data => {
          this.products = data;
          this.filteredProducts = [...this.products];
          this.sortProductsByDefault();
        },
        error: error => {
          let errorMessage = error?.message || 'Something went wrong!';
          this.toastr.error(errorMessage, 'Error');
        }
      });
  }

  onFilterChange(filters: { searchName: string, quantityRange: [number, number], priceRange: [number, number] }) {
    this.productFilteringService.filterProducts(this.products, filters)
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe({
        next: filteredProducts => {
          this.filteredProducts = filteredProducts;
          this.onSortChange(this.currentSortOption)
        },
        error: error => {
          let errorMessage = error?.message || 'Something went wrong!';
          this.toastr.error(errorMessage, 'Error');
        }
      });
  }

  onSortChange(sortOption: string) {
    this.currentSortOption = sortOption;
    this.productSortingService.sortProducts(this.filteredProducts, sortOption)
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe({
        next: sortedProducts => {
          this.filteredProducts = sortedProducts;
        },
        error: () => {
          this.toastr.error('Error while sorting products', 'Error');
        }
      });
  }

  selectProductForPurchase(productId: number): void {
    this.router.navigate(['/purchases', productId]);
  }

  private sortProductsByDefault(): void {
    this.onSortChange(this.currentSortOption);
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

}
