export type TBread = {
  breadId: number;
  breadName: string;
  imgUrl: string;
  stock: number;
  maxTingNum: number;
  tingDateTime: string;
  tingStatus: string;
};

export type TUserInfo = {
  nickname: string;
  banCount: number;
};

export type TStoreInfo = {
  storeId: number;
  storeName: string;
  imgUrl: string;
  imgUrl2: string;
  description: string;
  location: string;
  followerNum: number;
  rating: number;
};

export type TStoreRanking = {
  storeInfoList: TStoreInfo[];
  storeRankingList: TStoreInfo[];
};
