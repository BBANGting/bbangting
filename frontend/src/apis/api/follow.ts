import { defaultInstance } from '../utils/instance';

export const storeFollow = async (data: TFollowData) => {
  const res = await defaultInstance.post('store/follow', data);
  return res;
};

export const isFollow = async (storeId: number, userId: number) => {
  const res = await defaultInstance.get(`store/${storeId}/${userId}`);
  return res;
};

type TFollowData = {
  userId: number;
  storeId: number;
};
