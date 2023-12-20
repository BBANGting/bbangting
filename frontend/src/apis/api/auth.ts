import { AxiosResponse } from 'axios';
import { authInstance, defaultInstance } from '../utils/instance';

export const userLogin = async (data: FormData) => {
  const res: AxiosResponse = await defaultInstance.post('/auth/login', data, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  });
  return res;
};

export const userSignup = async (data: TSignupForm) => {
  const res = await defaultInstance.post('/auth/join', data);
  return res;
};

export const checkToken = async () => {
  const res = await authInstance.get('checkToken');
  return res;
};

type TSignupForm = {
  username: string;
  nickname: string;
  email: string;
  password: string;
};
