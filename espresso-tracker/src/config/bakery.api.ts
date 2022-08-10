import { AxiosObservable } from "axios-observable/lib/axios-observable.interface";
import { axiosInstance } from "../config/AxiosConfig";
import { Bakery } from "../models/Bakery";

export interface BakeryRequest {
  name: string;
  address: string;
  phone: string;
  webSite: string;
  imgSource?: string;
}

export const useBakeryApi = () => {

  const getBakeries = (): AxiosObservable<Bakery[]> =>
    axiosInstance.get<Bakery[]>(`/bakeries`);

  const createBakery = (request: BakeryRequest): AxiosObservable<number> =>
    axiosInstance.post<number>(`/bakeries`, request);

  return { getBakeries, createBakery }
}