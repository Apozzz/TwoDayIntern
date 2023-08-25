import { Component, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { GenericSortingService } from '@services/generic-sorting.service';
import { Subject } from 'rxjs';
import { debounceTime } from 'rxjs/operators';
import { SortConfig } from 'src/app/shared/models/sort-config.interface';
import { SortOption } from 'src/app/shared/models/sort-options.interface';
import { isEqual } from 'lodash';

@Component({
  selector: 'app-generic-sort',
  templateUrl: './generic-sort.component.html',
  styleUrls: ['./generic-sort.component.less']
})
export class GenericSortComponent<T> implements OnInit, OnChanges {

  private sortChangedSubject = new Subject<T[]>();

  @Input() data: T[] = [];
  @Input() sortOptions: SortOption[] = [];
  @Input() sortConfigs: SortConfig<T>[] = [];
  @Output() sortedData = this.sortChangedSubject.pipe(debounceTime(1000));

  currentSortOption: string = '';

  constructor(private sortService: GenericSortingService<T>) { }

  ngOnChanges(changes: SimpleChanges): void {
    const changesData = changes['data'];

    if (changesData && !isEqual(changesData.currentValue, changesData.previousValue) && !changesData.isFirstChange()) {
      this.data = changesData.currentValue;
      this.handleSortChange();
    }
  }

  ngOnInit(): void {
    if (!this.currentSortOption && this.sortOptions && this.sortOptions.length > 0) {
      this.currentSortOption = this.sortOptions[0].value;
    }

    this.handleSortChange();
  }

  handleSortChange(): void {
    if (this.data.length === 0) {
      return;
    }

    const sortedResults = this.sortService.sortData(this.data, this.sortConfigs, this.currentSortOption);
    this.sortChangedSubject.next(sortedResults);
  }

}
