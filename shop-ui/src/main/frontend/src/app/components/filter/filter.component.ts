import { ChangeDetectionStrategy, Component, Input, OnInit, Output } from "@angular/core";
import { FilteringService } from "@services/filtering.service";
import { Subject, debounceTime } from "rxjs";
import { CriterionRange } from "src/app/shared/models/criterion-range.interface";
import { RangeValueFilterCriterion } from "src/app/shared/models/range-value-filter-criterion.interface";
import { SingleValueFilterCriterion } from "src/app/shared/models/single-value-filter-criterion.interface";
import { FilterCriterion } from "src/app/shared/types/filter-criterion.type";

@Component({
  selector: 'app-filter',
  templateUrl: './filter.component.html',
  styleUrls: ['./filter.component.less'],
})
export class FilterComponent<T> implements OnInit {

  private filterSubject = new Subject<T[]>();
  @Input() data: T[] = [];
  @Input() fields: Array<keyof T> = [];
  @Output() filterChanged = this.filterSubject.pipe(debounceTime(1000));
  criterionRanges: CriterionRange<T>[] = [];
  criteria: FilterCriterion<T>[] = [];

  constructor(private filterService: FilteringService) { }

  ngOnInit() {
    this.criteria = this.filterService.initializeCriteriaFromData(this.data, this.fields);
    this.populateCriterionRanges();
  }

  handleFilterChange() {
    const filteredData = this.filterService.filterData(this.data, this.criteria);
    this.filterSubject.next(filteredData);
  }

  isSingleValueCriterion(criterion: FilterCriterion<T>): criterion is SingleValueFilterCriterion<T> {
    return 'value' in criterion;
  }

  isRangeValueCriterion(criterion: FilterCriterion<T>): criterion is RangeValueFilterCriterion<T> {
    return 'minValue' in criterion && 'maxValue' in criterion;
  }

  getCriterionRange(field: keyof T): CriterionRange<T> {
    return this.criterionRanges.find(cr => cr.field === field)!;
  }

  private populateCriterionRanges(): void {
    this.fields.forEach(field => {
      if (this.isFieldNumerical(field)) {
        const min = this.filterService.getMinValueFromData(this.data, field);
        const max = this.filterService.getMaxValueFromData(this.data, field);
        this.criterionRanges.push({
          field: field,
          min: min,
          max: max
        });
      }
    });
  }

  private isFieldNumerical(field: keyof T): boolean {
    return typeof this.data[0][field] === 'number';
  }

}
