import { Container, Grid, Typography } from '@mui/material';
import StarsIcon from '@mui/icons-material/Stars';
import StoreContainer from '../components/Store/StoreContainer';
import Search from '../components/Store/Search';

export const Store = () => {
  return (
    <Container fixed style={{ marginTop: 40, paddingLeft: 40 }}>
      <Typography variant="h5" sx={{ fontWeight: 600, marginBottom: 5 }}>
        스토어
      </Typography>
      <Grid display="flex">
        <Grid flexGrow={1} display="flex" alignItems="center">
          <StarsIcon />
          <Typography variant="h6" fontWeight="600" ml={1}>
            RANK
          </Typography>
        </Grid>
        <Search />
      </Grid>
      <StoreContainer />
    </Container>
  );
};
