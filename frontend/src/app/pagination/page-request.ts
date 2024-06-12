import {Sort} from "./sort";

export interface PageRequest<T> {
  page: number;
  size: number;
  sort?: Sort<T>;
}

export function defaultPageRequest<T>(): PageRequest<T> {
  return {
    page: 0,
    size: 10
  };
}
