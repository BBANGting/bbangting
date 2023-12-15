import { Container, Grid, Typography } from '@mui/material';
import StoreBreadCard from '../StoreDetail/StoreBreadCard';
import { useBreadList } from '../../hooks/useBreadList';
import { TBread } from '../../types';

const Lineup = () => {
  const { isLoading, data: breadList } = useBreadList();

  return (
    <Container fixed style={{ marginTop: 40, padding: 0 }}>
      <Typography variant="h5" sx={{ fontWeight: 600, marginBottom: 5 }}>
        빵케팅 라인업
      </Typography>
      <Grid container>
        {isLoading && <>Loading...</>}
        {breadList &&
          breadList.map((item: TBread, idx: number) => (
            <StoreBreadCard
              key={idx}
              breadId={item.breadId}
              breadName={item.breadName}
              breadImage={item.imgUrl}
              tingTime={item.tingDateTime}
              tingStatus="Y"
            />
          ))}
      </Grid>
    </Container>
  );
};

export default Lineup;
