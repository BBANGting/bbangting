import { useQuery } from 'react-query';
import { getStoreInfo } from '../apis/api/storeInfo';

export const useStoreDetail = (storeId: string) => {
  return useQuery(['getStoreDetail', storeId], () => getStoreInfo(storeId), {
    onSuccess: data => {
      console.log(data);
    },
    onError: err => {
      console.log(err);
    },
  });
};
