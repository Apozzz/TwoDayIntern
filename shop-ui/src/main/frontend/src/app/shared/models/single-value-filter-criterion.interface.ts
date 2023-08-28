export interface SingleValueFilterCriterion<T> {
    field: keyof T;
    value: T[keyof T];
}