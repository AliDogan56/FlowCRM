import {SortOrder} from "./SortOrderDTO";

export interface BaseSearchDTO extends SortOrder {
    page: number;
    pageSize?: number;
    totalPages?: number;
    totalElements?: number;
    hasNext?: boolean;
}