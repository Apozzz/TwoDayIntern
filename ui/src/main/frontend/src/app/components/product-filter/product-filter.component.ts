import { Component, Input, OnInit, Output } from '@angular/core';
import { ProductFilteringService } from '@services/product-filtering.service';
import { ProductDto } from '@services/product.service';
import { Subject } from 'rxjs';
import { debounceTime } from 'rxjs/operators';

@Component({
  selector: 'app-product-filter',
  templateUrl: './product-filter.component.html',
  styleUrls: ['./product-filter.component.less']
})
export class ProductFilterComponent implements OnInit {

  private filterChangedSubject = new Subject<{ searchName: string, quantityRange: [number, number], priceRange: [number, number] }>();
  
  @Output() filterChanged = this.filterChangedSubject.pipe(debounceTime(1500));
  @Input() productData: ProductDto[] = [];

  minPrice: number = 0;
  maxPrice: number = 0;
  minQuantity: number = 0;
  maxQuantity: number = 0;
  searchName: string = '';
  quantityRange: [number, number] = [this.minQuantity, this.maxQuantity];
  priceRange: [number, number] = [this.minPrice, this.maxPrice];

  constructor(private filterService: ProductFilteringService) { }

  ngOnInit() {
    if (this.productData && this.productData.length) {
      this.minPrice = this.filterService.getMinPrice(this.productData);
      this.maxPrice = this.filterService.getMaxPrice(this.productData);
      this.minQuantity = this.filterService.getMinQuantity(this.productData);
      this.maxQuantity = this.filterService.getMaxQuantity(this.productData);

      this.priceRange = [this.minPrice, this.maxPrice];
      this.quantityRange = [this.minQuantity, this.maxQuantity];
    }
  }

  onFilterChange() {
    this.filterChangedSubject.next({
      searchName: this.searchName,
      quantityRange: this.quantityRange,
      priceRange: this.priceRange
   });
  }

}
