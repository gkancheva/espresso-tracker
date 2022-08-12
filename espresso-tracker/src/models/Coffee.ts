import { Dayjs } from "dayjs";
import { Bakery } from "../models/Bakery";

export interface Coffee {
  id: number;
  name: string;
  bakery: Bakery;
  roastedOnDate: Dayjs;
  origin: string;
  description?: string;
}