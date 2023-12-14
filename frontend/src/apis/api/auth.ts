import { AxiosResponse } from 'axios';
import { defaultInstance } from '../utils/instance';

export const userLogin = async (data: TLoginForm) => {
  const res: AxiosResponse = await defaultInstance.post('/auth/login', data);
  return res;
};

export const userSignup = async (data: TSignupForm) => {
  const res = await defaultInstance.post('/auth/join', data);
  return res;
};

type TLoginForm = {
  email: string;
  password: string;
};

type TSignupForm = {
  username: string;
  nickname: string;
  email: string;
  password: string;
};
