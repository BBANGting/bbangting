import { atom } from 'recoil';
import { TBread } from '../types';

export const storeBreadState = atom<TBread[]>({
  key: 'storeBreadState',
  default: [],
});
