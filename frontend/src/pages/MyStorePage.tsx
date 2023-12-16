import { Container, Grid } from '@mui/material';
import StoreBanner from '../components/Mypage/MyStoreBanner';
import Profile from '../components/Mypage/Profile';
import MyStoreInfo from '../components/Mypage/MyStoreInfo';
import { useEffect, useState } from 'react';
import { getMyStoreInfo } from '../apis/api/mystore';

export const MyStorePage = () => {
  const [storeInfo, setStoreInfo] = useState();
  useEffect(() => {
    getMyStoreInfo(1).then(res => {
      console.log(res);
      setStoreInfo(res);
    });
  }, []);

  return (
    <>
      <StoreBanner />
      <Container disableGutters>
        {storeInfo && (
          <Grid container mt={10}>
            <Profile
              nickname={storeInfo.myStoreInfo.storeName}
              follower={storeInfo.myStoreInfo.followerNum}
              img={storeInfo.myStoreInfo.imgUrl}
            />
            <MyStoreInfo />
          </Grid>
        )}
      </Container>
    </>
  );
};
