import { atom } from 'recoil';
import { recoilPersist } from 'recoil-persist';

const { persistAtom } = recoilPersist();

export const userState = atom({
  key: 'userState',
  default: {
    userId: '',
    username: '',
  },
  effects_UNSTABLE: [persistAtom],
});
