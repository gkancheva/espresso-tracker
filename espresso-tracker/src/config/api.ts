import {AxiosObservable} from "axios-observable/lib/axios-observable.interface";
import {axiosInstance} from "../config/AxiosConfig";
import {EspressoSetting} from "../models/EspressoSetting";
import {useAuth} from "../services/AuthService";

export interface LoginRequest {
  email: string;
  password: string;
}

export interface RegisterRequest {
  email: string;
  username: string;
  password: string;
}

interface LoginResponse {
  username: string;
}

const { authData } = useAuth();

export const getEspressoSettings = (): AxiosObservable<EspressoSetting[]> =>
  axiosInstance.get<EspressoSetting[]>("/1/espresso-settings", {
    headers: { 'Authorization': 'Basic ' + authData }
  });

export const loginUser = (request: LoginRequest): AxiosObservable<LoginResponse> =>
  axiosInstance.post<LoginResponse>("/users/login", request);


export const registerUser = (request: RegisterRequest): AxiosObservable<string> =>
  axiosInstance.post<string>("/user/register", request);