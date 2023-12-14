import { AxiosResponse } from 'axios';
import { authInstance, defaultInstance } from '../utils/instance';

export const getUserInfo = async () => {
  try {
    const { data } = await authInstance.get('myPage');
    return data;
  } catch (err) {
    console.log(err);
  }
};

export const userLogin = async (data: TLoginForm) => {
  try {
    const res: AxiosResponse = await defaultInstance.post('/auth/login', data);
    return res;
  } catch (err) {
    console.log(err);
    return err;
  }
};

type TLoginForm = {
  email: string;
  password: string;
};
