import { AxiosRequestConfig } from 'axios';
import { Axios } from "axios-observable";
import { useAuth } from "../services/AuthService";

const axiosRequestConfiguration: AxiosRequestConfig = {
    baseURL: 'http://localhost:8080',
    responseType: 'json'
};

const initialization = (config: AxiosRequestConfig): Axios => Axios.create(config);

export const axiosInstance = initialization(axiosRequestConfiguration);

axiosInstance.interceptors.request.use(
  config => {
    const { getUser } = useAuth();
    config.headers = {
      'Content-Type': 'application/json'
    }
    if (getUser()) {
      config.headers['Authorization'] = 'Basic ' + getUser()?.authData
    }
    return config;
  }
);

const UNAUTHORIZED_STATUS_CODE = 401;

axiosInstance.interceptors.response.use(
  response => {
      return response
  }, (error) => {
      if (error.response.status === UNAUTHORIZED_STATUS_CODE) {
        (window as Window).location = '/login';
      }
  }
)