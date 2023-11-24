import { Container, Grid } from '@mui/material';
import Profile from '../components/Mypage/Profile';
import Information from '../components/Mypage/Information';
import StoreBanner from '../components/Mypage/StoreBanner';

export const MyPage = () => {
  return (
    <>
      <StoreBanner />
      <Container disableGutters>
        <Grid container mt={10} direction={'row'}>
          <Profile
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
