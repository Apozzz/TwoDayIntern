export interface SortConfig<T> {
    value: string;
    comparator: (a: T, b: T) => number;
}