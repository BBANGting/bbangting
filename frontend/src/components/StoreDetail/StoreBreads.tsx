import { Grid } from '@mui/material';
import StoreBreadCard from './StoreBreadCard';
import { TBread } from '../../types';

type StoreBreadsProps = {
  bread: TBread[];
};

const StoreBreads: React.FC<StoreBreadsProps> = ({ bread }) => {
  return (
    <Grid container mt={5}>
      {bread.map(item => (
        <StoreBreadCard
          breadId={item.breadId}
          breadImage={item.imgUrl}
          breadName={item.breadName}
          tingTime={item.tingDateTime}
          tingStatus={item.tingStatus}
        />
      ))}
    </Grid>
  );
};

export default StoreBreads;
