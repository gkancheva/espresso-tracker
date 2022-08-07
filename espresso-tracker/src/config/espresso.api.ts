import { AxiosObservable } from "axios-observable/lib/axios-observable.interface";
import { EspressoSetting } from "../models/EspressoSetting";
import { axiosInstance } from "../config/AxiosConfig";
import { useApiAuth } from "../config/api";

export const useEspressoApi = () => {
  const { headers, username } = useApiAuth();

  const getEspressoSettings = (): AxiosObservable<EspressoSetting[]> => {
    return axiosInstance.get<EspressoSetting[]>(`/${username()}/espresso-settings`, headers());
  }

  return { getEspressoSettings }
}