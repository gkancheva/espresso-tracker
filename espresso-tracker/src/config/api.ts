import { AxiosObservable } from "axios-observable/lib/axios-observable.interface";
import { axiosInstance } from "../config/AxiosConfig";

export interface LoginRequest {
  username: string;
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

export const loginUser = (request: LoginRequest): AxiosObservable<LoginResponse> =>
  axiosInstance.post<LoginResponse>("/users/login", request);


export const registerUser = (request: RegisterRequest): AxiosObservable<string> =>
  axiosInstance.post<string>("/users/register", request);