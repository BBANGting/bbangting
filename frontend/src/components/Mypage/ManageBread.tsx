import { Grid } from '@mui/material';
import InfoTitle from './InfoTitle';
import ManageBreadCard from './ManageBreadCard';
import bread from '../json/bread.json';

const ManageBread: React.FC = () => {
  return (
    <>
      <InfoTitle sx={{ mt: 15 }}>빵 관리</InfoTitle>
      <Grid container sx={{ overflowX: 'auto' }}>
        {bread.map(item => (
          <ManageBreadCard img={item.breadImage} name={item.breadName} />
        ))}
      </Grid>
    </>
  );
};

export default ManageBread;
