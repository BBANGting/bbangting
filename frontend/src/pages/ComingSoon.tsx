import { Container, Grid, Typography } from '@mui/material';
import { BreadCard } from '../components/Home/BreadCard';

import { getComingBread } from '../apis/api/comingBread';
import { useEffect, useState } from 'react';
import { TBread } from '../types';

const daysOfWeek = ['일', '월', '화', '수', '목', '금', '토'];

const generateDays = () => {
  const result = [];
  for (let i = 1; i <= 6; i++) {
    const date = new Date();
    date.setDate(date.getDate() + i);
    const day = daysOfWeek[date.getDay()];
    const month = date.getMonth() + 1;
    const dayOfMonth = date.getDate();
    result.push({
      label: `${month}월 ${dayOfMonth}일(${day})`,
      date: `${date.getFullYear()}-${month
        .toString()
        .padStart(2, '0')}-${dayOfMonth.toString().padStart(2, '0')}`,
    });
  }
  return result;
};

export const ComingSoon = () => {
  const [breadList, setBreadList] = useState<TBread[]>([]);

  useEffect(() => {
    getComingBread().then(res => {
      console.log(res);
      setBreadList(res);
    });
  }, []);

  return (
    <Container fixed style={{ marginTop: 40, padding: 0 }}>
      <Typography variant="h5" sx={{ fontWeight: 600, marginBottom: 5 }}>
        오픈 예정
      </Typography>
      <Grid container display="block">
        {generateDays().map((day, idx) => (
          <>
            <Typography key={idx} variant="h6" fontWeight={600} mb={2}>
              {day.label}
            </Typography>
            <Grid container display="flex">
              {breadList.filter(
                bread => bread.tingDateTime.split('T')[0] === day.date,
              ).length === 0 ? (
                <Typography variant="h5" mb={2} color="#c7c7c7">
                  오픈 예정인 빵켓팅이 없습니다.
                </Typography>
              ) : (
                breadList
                  .filter(
                    bread => bread.tingDateTime.split('T')[0] === day.date,
                  )
                  .map((bread, idx) => (
                    <BreadCard
                      key={idx}
                      store={bread.storeName}
                      name={bread.breadName}
                      img={bread.imgUrl}
                      openTime={bread.tingDateTime.split('T')[1]}
                    />
                  ))
              )}
            </Grid>
          </>
        ))}
      </Grid>
    </Container>
  );
};
