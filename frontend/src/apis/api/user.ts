import { authInstance } from '../utils/instance';

export const getUserInfo = async () => {
  try {
    const { data } = await authInstance.get('myPage');
    return data;
  } catch (err) {
    console.log(err);
  }
};
