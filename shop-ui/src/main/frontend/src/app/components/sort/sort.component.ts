import { Component, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { SortingService } from '@services/sorting.service';
import { isEqual } from 'lodash';
import { Subject } from 'rxjs';
import { debounceTime } from 'rxjs/operators';
import { SortConfig } from 'src/app/shared/models/sort-config.interface';
import { SortOption } from 'src/app/shared/models/sort-options.interface';

@Component({
  selector: 'app-sort',
  templateUrl: './sort.component.html',
  styleUrls: ['./sort.component.less']
})
export class SortComponent<T> implements OnInit, OnChanges {

  private sortSubject = new Subject<T[]>();

  @Input() data: T[] = [];
  @Input() sortOptions: SortOption[] = [];
  @Input() sortConfigs: SortConfig<T>[] = [];
  @Output() sortedData = this.sortSubject.pipe(debounceTime(1000));

  currentSortOption: string = '';

  constructor(private sortService: SortingService<T>) { }

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
    this.sortSubject.next(sortedResults);
  }

}
