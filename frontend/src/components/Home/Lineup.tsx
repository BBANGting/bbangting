import { Container, Grid, Typography } from '@mui/material';
import { useEffect } from 'react';
import axios from 'axios';
import { useQuery } from 'react-query';
import { ReactQueryDevtools } from 'react-query/devtools';
import { BreadCard } from './BreadCard';
import StoreBreadCard from '../StoreDetail/StoreBreadCard';

const Lineup = () => {
  const onSuccess = (data: any) => {
    console.log(data.data);
  };
  const {
    isLoading,
    data: breadList,
    isError,
    error,
  } = useQuery(
    'getBread',
    () => {
      return axios.get('http://localhost:8080/');
    },
    { onSuccess },
  );
  useEffect(() => {}, []);
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
        {/**TODO: 이미지 해결 */}
        {breadList.data.map((item, idx) => (
          <StoreBreadCard
            breadId={item.breadId}
            breadName={item.breadName}
            breadImage={item.imgUrl}
            tingTime={item.tingDateTime}
            tingStatus="Y"
          />
        ))}
        <BreadCard
          store="성심당"
          name="튀김소보로"
          img="/imgs/soboro.png"
          openTime="10:00"
        />
      </Grid>
      <ReactQueryDevtools position="bottom-right" />
    </Container>
  );
};

export default Lineup;
