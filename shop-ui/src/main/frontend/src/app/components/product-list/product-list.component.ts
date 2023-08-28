import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { GenericSortComponent } from '@components/generic-sort/generic-sort.component';
import { ProductService } from '@services/product.service';
import { Subject, takeUntil } from 'rxjs';
import { ProductDto } from 'src/app/shared/models/product-dto.interface';
import { SortConfig } from 'src/app/shared/models/sort-config.interface';
import { SortOption } from 'src/app/shared/models/sort-options.interface';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.less']
})
export class ProductListComponent implements OnInit, OnDestroy {

  originalData: ProductDto[] = [];
  filteredAndSortedProducts: ProductDto[] = [];
  sortOptions: SortOption[] = [];
  sortConfigs: SortConfig<ProductDto>[] = [];
  filterFields: Array<keyof ProductDto> = [];
  private unsubscribe$ = new Subject<void>();

  @ViewChild('sortComp', { static: false }) sortComponent!: GenericSortComponent<any>;

  constructor(
    //private productService: ProductService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.fetchProducts();
    this.setSortOptions();
    this.setSortConfigs();
    this.setFilterFields();
  }

  fetchProducts(): void {
    const products = this.route.snapshot.data['products'];
    this.originalData = products;
    this.filteredAndSortedProducts = [...this.originalData];
    // this.route.snapshot.data['products']
    //   .pipe(takeUntil(this.unsubscribe$))
    //   .subscribe((data: ProductDto[]) => {
    //     this.originalData = data;
    //     this.filteredAndSortedProducts = [...this.originalData];
    //   });
  }

  handleFilterChange(filteredProducts: ProductDto[]) {
    this.filteredAndSortedProducts = filteredProducts;
  }

  handleSortChange(sortedProducts: ProductDto[]): void {
    this.filteredAndSortedProducts = sortedProducts;
  }

  selectProductForPurchase(productId: number): void {
    this.router.navigate(['/purchase', productId]);
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  setFilterFields(): void {
    this.filterFields = ['name', 'quantity', 'finalPrice'];
  }

  setSortOptions(): void {
    this.sortOptions = [
      { value: 'name-asc', label: 'Name (Low to High)' },
      { value: 'quantity-asc', label: 'Quantity (Low to High)' },
      { value: 'quantity-desc', label: 'Quantity (High to Low)' },
      { value: 'final-price-asc', label: 'FinalPrice (Low to High)' },
      { value: 'final-price-desc', label: 'FinalPrice (High to Low)' },
    ];
  }

  setSortConfigs(): void {
    this.sortConfigs = [
      {
        value: 'name-asc',
        comparator: (a, b) => a.name.localeCompare(b.name)
      },
      {
        value: 'quantity-asc',
        comparator: (a, b) => a.quantity - b.quantity
      },
      {
        value: 'quantity-desc',
        comparator: (a, b) => b.quantity - a.quantity
      },
      {
        value: 'final-price-asc',
        comparator: (a, b) => a.finalPrice - b.finalPrice
      },
      {
        value: 'final-price-desc',
        comparator: (a, b) => b.finalPrice - a.finalPrice
      },
    ];
  }

}
