import { Grid } from '@mui/material';
import { TBread } from '../../types';
import BreadCard from '../common/Breadcard';

type StoreBreadsProps = {
  bread: TBread[];
};

const StoreBreads: React.FC<StoreBreadsProps> = ({ bread }) => {
  return (
    <Grid container mt={5}>
      {bread.map(item => (
        <BreadCard
          key={item.breadId}
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
