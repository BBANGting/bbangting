import { defaultInstance } from '../utils/instance';

export const storeFollow = async (data: TFollowData) => {
  const res = await defaultInstance.post('store/follow', data);
  return res;
};

type TFollowData = {
  userId: number;
  storeId: number;
};
