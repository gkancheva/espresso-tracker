import { AxiosObservable } from "axios-observable/lib/axios-observable.interface";
import { EspressoSetting } from "../models/EspressoSetting";
import { axiosInstance } from "../config/AxiosConfig";
import { CoffeeRequest } from "../services/CoffeeService";

export const useCoffeeApi = () => {

  const createCoffeeApi = (request: CoffeeRequest): AxiosObservable<EspressoSetting[]> =>
    axiosInstance.post<EspressoSetting[]>(`/coffees`, request);

  return { createCoffeeApi }
}