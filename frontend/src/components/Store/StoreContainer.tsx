import { Grid } from '@mui/material';
import StoreCard from './StoreCard';

const StoreContainer = () => {
  return (
    <Grid container alignItems="center" height={'100%'}>
      <StoreCard img="/imgs/touslesjours.png" name="뚜레쥬르 성수점" />
      <StoreCard img="/imgs/bbangzip.png" name="대전 성심당 본점" />
      <StoreCard img="/imgs/touslesjours.png" name="뚜레쥬르 성수점" />
      <StoreCard img="/imgs/touslesjours.png" name="뚜레쥬르 성수점" />
      <StoreCard img="/imgs/touslesjours.png" name="뚜레쥬르 성수점" />
      <StoreCard img="/imgs/touslesjours.png" name="뚜레쥬르 성수점" />
    </Grid>
  );
};

export default StoreContainer;
