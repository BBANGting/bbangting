import { authInstance } from '../utils/instance';

export const getMyStoreInfo = async (userId: number) => {
  try {
    const { data } = await authInstance.get(`myStore/${userId}`);
    return data;
  } catch (err) {
    console.log(err);
  }
};
