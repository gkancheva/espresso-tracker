import { AxiosObservable } from "axios-observable/lib/axios-observable.interface";
import { EspressoSetting } from "../models/EspressoSetting";
import { axiosInstance } from "../config/AxiosConfig";
import { useAuth } from "../services/AuthService";

export const useEspressoApi = () => {
  const { getUser } = useAuth();

  const username = () => getUser()?.username

  const getEspressoSettings = (): AxiosObservable<EspressoSetting[]> =>
    axiosInstance.get<EspressoSetting[]>(`/${username()}/espresso-settings`);

  return { getEspressoSettings }
}