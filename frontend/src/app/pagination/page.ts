export interface Page<T> {
  content: T[];
  totalPages: number;
  totalElements: number;
  first: boolean;
  last: boolean;
  number: number;
  size: number;
  numberOfElements: number;
  empty: boolean;
}

export function emptyPage() {
  return {
    content: [],
    totalPages: 0,
    totalElements: 0,
    first: false,
    last: false,
    number: 0,
    size: 0,
    numberOfElements: 0,
    empty: true,
  };
}

export function toEmptyPage<T>(value: Page<T> | null): Page<T> {
  return value === null ? emptyPage() : value;
}
