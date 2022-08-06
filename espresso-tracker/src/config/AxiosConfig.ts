import { AxiosRequestConfig } from 'axios';
import {Axios} from "axios-observable";

const axiosRequestConfiguration: AxiosRequestConfig = {
    baseURL: 'http://localhost:8080',
    responseType: 'json',
    headers: {
        'Content-Type': 'application/json',
    },
};

const initialization = (config: AxiosRequestConfig): Axios => Axios.create(config);

export const axiosInstance = initialization(axiosRequestConfiguration);