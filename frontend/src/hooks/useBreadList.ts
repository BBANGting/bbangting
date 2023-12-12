import { UseQueryOptions, useQuery } from 'react-query';
import { defaultInstance } from '../apis/utils/instance';
import { BreadList } from '../types';

const getBreadList = async () => {
  try {
    const response = await defaultInstance.get('');
    return response.data;
  } catch (err) {
    console.log(err);
  }
};

export const useBreadList = (
  options?: UseQueryOptions<BreadList[], Error, BreadList[], string[]>,
) => {
  return useQuery(['getBread'], getBreadList, {
    ...options,
    onSuccess: data => {
      console.log(data);
    },
    onError: err => {
      console.log(err);
    },
  });
};
