import axios, { AxiosHeaders, AxiosRequestConfig } from 'axios';

const BASE_URL = import.meta.env.VITE_SERVER_URL;

const axiosApi = (url: string, options?: AxiosRequestConfig) => {
  const instance = axios.create({
    baseURL: url,
    ...options,
  });
  return instance;
};

const axiosAuthApi = (url: string, options?: AxiosHeaders) => {
  const token = localStorage.getItem('access-token');
  const instance = axios.create({
    baseURL: url,
    headers: {
      Authorization: `Bearer ${token}`,
      ...options,
    },
  });
  return instance;
};

export const defaultInstance = axiosApi(BASE_URL);
export const authInstance = axiosAuthApi(BASE_URL);
