import { RangeValueFilterCriterion } from "../models/range-value-filter-criterion.interface";
import { SingleValueFilterCriterion } from "../models/single-value-filter-criterion.interface";

export type GenericFilterCriterion<T> = SingleValueFilterCriterion<T> | RangeValueFilterCriterion<T>;