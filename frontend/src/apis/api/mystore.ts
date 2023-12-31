import { authInstance } from '../utils/instance';

export const getMyStoreInfo = async () => {
  try {
    const { data } = await authInstance.get(`myStore`);
    return data;
  } catch (err) {
    console.log(err);
  }
};

export const newMyStore = async formData => {
  const res = await authInstance.post(`myStore/new`, formData, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  });
  return res;
};
