import {Sort} from "@angular/material/sort";

export interface PageRequest {
  page: number;
  size: number;
  sort?: Sort;
}
