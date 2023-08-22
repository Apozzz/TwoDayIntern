import { Component, Input, OnInit, Output } from '@angular/core';
import { ProductFilteringService } from '@services/product-filtering.service';
import { Subject } from 'rxjs';
import { debounceTime } from 'rxjs/operators';
import { FilterData } from 'src/app/shared/models/filter-data.interface';
import { ProductDto } from 'src/app/shared/models/product-dto.interface';

@Component({
  selector: 'app-product-filter',
  templateUrl: './product-filter.component.html',
  styleUrls: ['./product-filter.component.less']
})
export class ProductFilterComponent implements OnInit {

  private filterChangedSubject = new Subject<FilterData>();
  
  @Output() filterChanged = this.filterChangedSubject.pipe(debounceTime(1000));
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
    if (this.productData?.length) {
      this.minPrice = this.filterService.getMinPrice(this.productData);
      this.maxPrice = this.filterService.getMaxPrice(this.productData);
      this.minQuantity = this.filterService.getMinQuantity(this.productData);
      this.maxQuantity = this.filterService.getMaxQuantity(this.productData);

      this.priceRange = [this.minPrice, this.maxPrice];
      this.quantityRange = [this.minQuantity, this.maxQuantity];
    }
  }

  onFilterChange() {
    const data: FilterData = {
      searchName: this.searchName,
      quantityRange: this.quantityRange,
      priceRange: this.priceRange
    };
  
    this.filterChangedSubject.next(data);
  }

}
