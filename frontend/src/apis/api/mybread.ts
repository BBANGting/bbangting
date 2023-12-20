import { authInstance } from '../utils/instance';

export const newMyBread = async formdata => {
  try {
    const res = await authInstance.post(`myStore/bread/new`, formdata);
    return res;
  } catch (err) {
    console.log(err);
  }
};
