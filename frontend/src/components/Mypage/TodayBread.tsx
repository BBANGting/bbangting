import { Grid } from '@mui/material';
import InfoTitle from './InfoTitle';
import TodayBreadCard from './TodayBreadCard';

const TodayBread: React.FC = () => {
  return (
    <Grid>
      <InfoTitle>오늘의 빵팅</InfoTitle>
      <TodayBreadCard
        name="쪽파 크림치즈 베이글"
        curCount={2}
        maxCount={50}
        img={`/imgs/bagel.jpeg`}
      />
      <TodayBreadCard
        name="튀김소보로"
        curCount={1}
        maxCount={40}
        img={`/imgs/soboro.png`}
      />
    </Grid>
  );
};

export default TodayBread;
