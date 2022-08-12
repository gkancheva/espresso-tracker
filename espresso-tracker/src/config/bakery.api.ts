import { AxiosObservable } from "axios-observable/lib/axios-observable.interface";
import { axiosInstance } from "../config/AxiosConfig";
import { Bakery, BakeryWithCoffees } from "../models/Bakery";
import { BakeryRequest } from "../services/BakeryService";

export const getBakeriesApi = (): AxiosObservable<Bakery[]> =>
  axiosInstance.get<Bakery[]>(`/bakeries`);

export const createBakeryApi = (request: BakeryRequest): AxiosObservable<number> =>
  axiosInstance.post<number>(`/bakeries`, request);

export const getBakeryApi = (id: number) =>
  axiosInstance.get<BakeryWithCoffees>(`/bakeries/${id}`);