import { defaultInstance } from '../utils/instance';

export const getBreadInfo = async (breadId: number) => {
  try {
    const { data } = await defaultInstance.get(`bread/${breadId}`);
    return data;
  } catch (err) {
    console.log(err);
  }
};
