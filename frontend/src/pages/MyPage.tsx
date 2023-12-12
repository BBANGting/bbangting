import { Container, Grid } from '@mui/material';
import Profile from '../components/Mypage/Profile';
import Information from '../components/Mypage/Information';
import MyStoreBanner from '../components/Mypage/MyStoreBanner';
import { getUserInfo } from '../apis/api/user';
import { useEffect, useState } from 'react';
import { UserInfo } from '../types';

export const MyPage = () => {
  const [userInfo, setUserInfo] = useState<UserInfo | undefined>();
  useEffect(() => {
    getUserInfo().then(res => {
      console.log(res);
      setUserInfo(res.userInfo[0]);
    });
  }, []);
  return (
    <>
      <MyStoreBanner />
      <Container disableGutters>
        <Grid container mt={10} direction={'row'}>
          <Profile
            img="/imgs/profile.png"
            nickname={userInfo.nickname}
            name="김진우"
            email="rlawlsdn316@gmail.com"
            warning={userInfo.banCount}
          />
          <Information />
        </Grid>
      </Container>
    </>
  );
};
