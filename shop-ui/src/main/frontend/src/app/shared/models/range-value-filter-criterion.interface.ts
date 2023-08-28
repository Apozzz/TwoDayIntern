export interface RangeValueFilterCriterion<T> {
    field: keyof T;
    minValue?: number;
    maxValue?: number;
}