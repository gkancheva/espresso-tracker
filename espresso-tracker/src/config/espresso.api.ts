import { AxiosObservable } from "axios-observable/lib/axios-observable.interface";
import {EspressoSetting, EspressoSettingRequest} from "../models/EspressoSetting";
import { axiosInstance } from "../config/AxiosConfig";

export const getEspressoSettingsApi = (): AxiosObservable<EspressoSetting[]> =>
  axiosInstance.get<EspressoSetting[]>(`/espresso-settings`);

export const createEspressoSettingApi = (request: EspressoSettingRequest): AxiosObservable<number> =>
  axiosInstance.post<number>('/espresso-settings', request);