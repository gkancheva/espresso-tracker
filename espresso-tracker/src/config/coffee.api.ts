import { AxiosObservable } from "axios-observable/lib/axios-observable.interface";
import { axiosInstance } from "../config/AxiosConfig";
import { CoffeeRequest } from "../services/CoffeeService";
import { Coffee } from "../models/Coffee";

export const createCoffeeApi = (request: CoffeeRequest): AxiosObservable<Coffee[]> =>
  axiosInstance.post<Coffee[]>(`/coffees`, request);

export const getCoffeesApi = (): AxiosObservable<Coffee[]> =>
  axiosInstance.get<Coffee[]>(`/coffees`);