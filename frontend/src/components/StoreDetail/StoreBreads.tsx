import { Grid } from '@mui/material';
import StoreBreadCard from './StoreBreadCard';

type BreadType = {
  breadId: number;
  storeId: number;
  breadName: string;
  breadImage: string;
  content: string;
  price: string;
  tingTime: string;
  maxTingNum: number;
  stock: number;
  tingStatus: string;
};

type StoreBreadsProps = {
  bread: BreadType[];
};

const StoreBreads: React.FC<StoreBreadsProps> = ({ bread }) => {
  return (
    <Grid container>
      {bread.map(item => (
        <StoreBreadCard
          breadImage={item.breadImage}
          breadName={item.breadName}
          tingTime={item.tingTime}
          tingStatus={item.tingStatus}
        />
      ))}
    </Grid>
  );
};

export default StoreBreads;
