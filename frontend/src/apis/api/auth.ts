import { AxiosResponse } from 'axios';
import { defaultInstance } from '../utils/instance';

export const userLogin = async (data: TLoginForm) => {
  const res: AxiosResponse = await defaultInstance.post('/auth/login', data);
  return res;
};

type TLoginForm = {
  email: string;
  password: string;
};
