import { UseQueryOptions, useQuery } from 'react-query';
import { defaultInstance } from '../apis/utils/instance';
import { StoreRanking } from '../types';

const getStoreList = async () => {
  try {
    const response = await defaultInstance.get('/store');
    return response.data;
  } catch (err) {
    console.log(err);
  }
};

export const useStoreList = (
  options?: UseQueryOptions<StoreRanking, Error, StoreRanking, string[]>,
) => {
  return useQuery(['getStore'], getStoreList, {
    ...options,
    onSuccess: data => {
      console.log(data);
    },
    onError: err => {
      console.log(err);
    },
  });
};
