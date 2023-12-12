export type BreadList = {
  breadId: number;
  breadName: string;
  imgUrl: string;
  stock: number;
  maxTingNum: number;
  tingDateTime: string;
  tingStatus: string;
};

export type UserInfo = {
  nickname: string;
  banCount: number;
};

export type StoreInfo = {
  storeId: number;
  storeName: string;
  imgUrl: string;
  imgUrl2: string;
  description: string;
  location: string;
  followerNum: number;
  rating: number;
};

export type StoreRanking = {
  storeInfoList: StoreInfo[];
  storeRankingList: StoreInfo[];
};
