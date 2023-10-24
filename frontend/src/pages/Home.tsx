import { Container, Grid, Typography } from '@mui/material';
import { MainBanner } from '../components/Home/MainBanner';

export const Home = () => {
  return (
    <>
      <MainBanner />
      <Container fixed style={{ marginTop: 40, padding: 0 }}>
        <Typography
          variant="h5"
          sx={{ fontWeight: 600, marginLeft: 5, marginBottom: 5 }}
        >
          빵케팅 라인업
        </Typography>
        <Grid container>
          <Grid
            item
            xs={4}
            display={'flex'}
            direction="column"
            alignItems={'center'}
            mb={5}
          >
            <img
              src="/imgs/soboro.png"
              alt=".."
              style={{ width: 320, height: 250, marginBottom: 10 }}
            />
            <Typography sx={{ fontSize: 20 }}>[성심당] 튀김소보로</Typography>
          </Grid>
        </Grid>
      </Container>
    </>
  );
};
