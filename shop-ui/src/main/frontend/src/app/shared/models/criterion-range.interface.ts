export interface CriterionRange<T> {
    field: keyof T;
    min: number;
    max: number;
}