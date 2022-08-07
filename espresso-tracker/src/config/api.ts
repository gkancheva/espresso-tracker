import { AxiosObservable } from "axios-observable/lib/axios-observable.interface";
import { axiosInstance } from "../config/AxiosConfig";
import { useAuth } from "../services/AuthService";

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

export const useApiAuth = () => {
  const { getUser } = useAuth();

  const username = () => getUser()?.username;
  const authData = () => getUser()?.authData;

  const headers = () => {
    return {
      headers: { 'Authorization': 'Basic ' + authData() }
    }
  }

  return { headers, username };
}

export const loginUser = (request: LoginRequest): AxiosObservable<LoginResponse> =>
  axiosInstance.post<LoginResponse>("/users/login", request);


export const registerUser = (request: RegisterRequest): AxiosObservable<string> =>
  axiosInstance.post<string>("/users/register", request);