import { Container, Grid } from '@mui/material';
import StoreBanner from '../components/Mypage/StoreBanner';
import Profile from '../components/Mypage/Profile';
import StoreInformation from '../components/Mypage/StoreInformation';

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
          <StoreInformation />
        </Grid>
      </Container>
    </>
  );
};
