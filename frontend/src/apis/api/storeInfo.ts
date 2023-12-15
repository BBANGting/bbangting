import { defaultInstance } from '../utils/instance';

export const getStoreInfo = async (storeId: string) => {
  try {
    const { data } = await defaultInstance.get(`store/${storeId}`);
    return data;
  } catch (err) {
    console.log(err);
  }
};
