import { Grid } from '@mui/material';
import InfoTitle from './InfoTitle';
import ManageBreadCard from './ManageBreadCard';

const ManageBread: React.FC = () => {
  return (
    <>
      <InfoTitle>빵 관리</InfoTitle>
      <Grid container sx={{ overflowX: 'auto' }}>
        <ManageBreadCard img="/imgs/bagel.jpeg" name="쪽파 크림치즈 베이글" />
        <ManageBreadCard img="/imgs/bagel.jpeg" name="쪽파 크림치즈 베이글" />
        <ManageBreadCard img="/imgs/soboro.png" name="튀김 소보로" />
        <ManageBreadCard img="/imgs/soboro.png" name="튀김 소보로" />
      </Grid>
    </>
  );
};

export default ManageBread;
