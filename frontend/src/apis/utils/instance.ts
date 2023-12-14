import axios, {
  AxiosError,
  AxiosHeaders,
  AxiosRequestConfig,
  AxiosRequestHeaders,
  AxiosResponse,
} from 'axios';

const BASE_URL = import.meta.env.VITE_SERVER_URL;

const axiosApi = (url: string, options?: AxiosRequestConfig) => {
  const instance = axios.create({
    baseURL: url,
    ...options,
    timeout: 2000,
  });

  instance.interceptors.response.use(
    (response: AxiosResponse) => response,
    (error: AxiosError) => {
      return Promise.reject(error);
    },
  );

  return instance;
};

const axiosAuthApi = (url: string, options?: AxiosHeaders) => {
  const instance = axios.create({
    baseURL: url,
    headers: {
      ...options,
    },
  });

  instance.interceptors.request.use(
    config => {
      const token = localStorage.getItem('access-token');
      if (token) {
        config.headers = config.headers || {};
        (
          config.headers as AxiosRequestHeaders
        ).Authorization = `Bearer ${token}`;
      }
      return config;
    },
    error => {
      console.error(error);
      return Promise.reject(error);
    },
  );
  return instance;
};

export const defaultInstance = axiosApi(BASE_URL);
export const authInstance = axiosAuthApi(BASE_URL);
