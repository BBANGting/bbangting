import { Container, Grid } from '@mui/material';
import StoreBanner from '../components/Mypage/MyStoreBanner';
import Profile from '../components/Mypage/Profile';
import MyStoreInfo from '../components/Mypage/MyStoreInfo';
import { useEffect, useState } from 'react';
import { getMyStoreInfo } from '../apis/api/mystore';
import { useSetRecoilState } from 'recoil';
import { storeIdState } from '../store/store';
import { storeBreadState } from '../store/state';

export const MyStorePage = () => {
  const [storeInfo, setStoreInfo] = useState();

  const setStoreId = useSetRecoilState(storeIdState);
  const setStoreBread = useSetRecoilState(storeBreadState);

  useEffect(() => {
    getMyStoreInfo().then(res => {
      console.log(res);
      setStoreId(res.myStoreInfo.storeId);
      setStoreBread(res.breadList);
      setStoreInfo(res);
    });
  }, []);

  return (
    <>
      <StoreBanner />
      <Container disableGutters>
        {!storeInfo && (
          <Grid container mt={10}>
            <Profile
              nickname="빵팅 주인"
              follower={`0`}
              img={`/imgs/profile.png`}
            />
            <MyStoreInfo isStore={true} />
          </Grid>
        )}
        {storeInfo && (
          <Grid container mt={10}>
            <Profile
              nickname={storeInfo.myStoreInfo.storeName}
              follower={storeInfo.myStoreInfo.followerNum.toString()}
              img={storeInfo.myStoreInfo.imgUrl}
            />
            <MyStoreInfo />
          </Grid>
        )}
      </Container>
    </>
  );
};
