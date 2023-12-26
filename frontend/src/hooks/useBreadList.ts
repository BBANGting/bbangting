import { UseQueryOptions, useQuery } from 'react-query';
import { defaultInstance } from '../apis/utils/instance';
import { TBread } from '../types';

const getBread = async () => {
  try {
    const response = await defaultInstance.get('');
    return response.data;
  } catch (err) {
    console.log(err);
  }
};

export const useBreadList = (
  options?: UseQueryOptions<TBread[], Error, TBread[], string[]>,
) => {
  return useQuery(['getBread'], getBread, {
    ...options,
    onSuccess: data => {
      console.log(data);
    },
    onError: err => {
      console.log(err);
    },
  });
};
