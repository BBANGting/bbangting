import axios from 'axios';
import { useQuery } from 'react-query';

const fetchBreadList = () => {
  return axios.get(`http://localhost:8080`);
};

export const useBreadList = (onSuccess, onError) => {
  return useQuery('getBread', fetchBreadList, {
    onSuccess,
    onError,
  });
};
