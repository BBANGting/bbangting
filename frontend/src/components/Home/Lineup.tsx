import { Container, Grid, Typography } from '@mui/material';
import { ReactQueryDevtools } from 'react-query/devtools';
import StoreBreadCard from '../StoreDetail/StoreBreadCard';
import { useBreadList } from '../../hooks/useBreadList';

const Lineup = () => {
  const onSuccess = (data: any) => {
    console.log(data);
  };
  const onError = (error: ErrorEvent) => {
    console.log(error);
  };
  const { isLoading, data: breadList } = useBreadList(onSuccess, onError);

  return (
    <Container fixed style={{ marginTop: 40, padding: 0 }}>
      <Typography
        variant="h5"
        sx={{ fontWeight: 600, marginLeft: 5, marginBottom: 5 }}
      >
        빵케팅 라인업
      </Typography>
      <Grid container>
        {isLoading && <>Loading...</>}
        {!isLoading &&
          breadList?.data.map((item, idx) => (
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
      <ReactQueryDevtools position="bottom-right" />
    </Container>
  );
};

export default Lineup;
