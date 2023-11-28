import { Container, Grid } from '@mui/material';
import Profile from '../components/Mypage/Profile';
import Information from '../components/Mypage/Information';
import MyStoreBanner from '../components/Mypage/MyStoreBanner';

export const MyPage = () => {
  return (
    <>
      <MyStoreBanner />
      <Container disableGutters>
        <Grid container mt={10} direction={'row'}>
          <Profile
            img="/imgs/profile.JPG"
            nickname="비누"
            name="김진우"
            email="rlawlsdn316@gmail.com"
            warning={2}
          />
          <Information />
        </Grid>
      </Container>
    </>
  );
};
