import { Container, Grid } from '@mui/material';
import StoreBanner from '../components/Mypage/MyStoreBanner';
import Profile from '../components/Mypage/Profile';
import MyStoreInfo from '../components/Mypage/MyStoreInfo';

export const MyStorePage = () => {
  return (
    <>
      <StoreBanner />
      <Container disableGutters>
        <Grid container mt={10}>
          <Profile
            nickname={`뚜레쥬르 성수점`}
            follower={6}
            img={`/imgs/touslesjours.png`}
          />
          <MyStoreInfo />
        </Grid>
      </Container>
    </>
  );
};
