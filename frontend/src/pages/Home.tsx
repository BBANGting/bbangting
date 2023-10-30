import { Container, Grid, Typography } from '@mui/material';
import { MainBanner } from '../components/Home/MainBanner';
import { BreadCard } from '../components/Home/BreadCard';

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
          <BreadCard
            store="성심당"
            name="튀김소보로"
            img="/imgs/soboro.png"
            openTime="10:00"
          />
        </Grid>
      </Container>
    </>
  );
};
