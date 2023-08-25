import { ChangeDetectorRef, Component, EventEmitter, Input, OnInit, Output } from "@angular/core";
import { GenericFilteringService } from "@services/generic-filtering.service";
import { Subject, debounceTime } from "rxjs";
import { CriterionRange } from "src/app/shared/models/criterion-range.interface";
import { RangeValueFilterCriterion } from "src/app/shared/models/range-value-filter-criterion.interface";
import { SingleValueFilterCriterion } from "src/app/shared/models/single-value-filter-criterion.interface";
import { GenericFilterCriterion } from "src/app/shared/types/generic-filter-criterion.type";

@Component({
  selector: 'app-generic-filter',
  templateUrl: './generic-filter.component.html',
  styleUrls: ['./generic-filter.component.less']
})
export class GenericFilterComponent<T> implements OnInit {

  private filterChangedSubject = new Subject<T[]>();
  @Input() data: T[] = [];
  @Input() fields: Array<keyof T> = [];
  @Output() filterChanged = this.filterChangedSubject.pipe(debounceTime(1000));
  criterionRanges: CriterionRange<T>[] = [];
  criteria: GenericFilterCriterion<T>[] = [];

  constructor(private filterService: GenericFilteringService) { }

  ngOnInit() {
    this.criteria = this.filterService.initializeCriteriaFromData(this.data, this.fields);
    this.populateCriterionRanges();
  }

  handleFilterChange() {
    const filteredData = this.filterService.filterData(this.data, this.criteria);
    this.filterChangedSubject.next(filteredData);
  }

  isSingleValueCriterion(criterion: GenericFilterCriterion<T>): criterion is SingleValueFilterCriterion<T> {
    return 'value' in criterion;
  }

  isRangeValueCriterion(criterion: GenericFilterCriterion<T>): criterion is RangeValueFilterCriterion<T> {
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
