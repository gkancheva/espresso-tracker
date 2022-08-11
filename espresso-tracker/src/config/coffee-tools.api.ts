import { AxiosObservable } from "axios-observable/lib/axios-observable.interface";
import { axiosInstance } from "../config/AxiosConfig";
import { CoffeeToolRequest, CoffeeTools } from "../services/CoffeeToolsService";

export const createUpdateCoffeeToolApi = (request: CoffeeToolRequest): AxiosObservable<CoffeeTools> =>
  axiosInstance.put<CoffeeTools>(`/coffee-tools`, request);

export const getCoffeeToolsApi = (): AxiosObservable<CoffeeTools> =>
  axiosInstance.get<CoffeeTools>(`/coffee-tools`);
