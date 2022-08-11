import { Coffee } from "../models/Coffee";

export interface Bakery {
  id: number;
  name: string;
  address: string;
  phoneNumber: string;
  webSite: string;
  imgSrc?: string;
}

export interface BakeryWithCoffees {
  bakery: Bakery;
  coffees: Coffee[];
}