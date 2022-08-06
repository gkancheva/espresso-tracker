import {Dayjs} from "dayjs";

export interface Coffee {
  name: string;
  bakery: string;
  roastedOnDate: Dayjs;
}