import { defaultInstance } from '../utils/instance';

export const getComingBread = async () => {
  try {
    const { data } = await defaultInstance.get(`comingSoon`);
    return data;
  } catch (err) {
    console.log(err);
  }
};
