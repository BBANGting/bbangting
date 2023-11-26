import { Grid } from '@mui/material';
import TodayBread from './TodayBread';
import ManageBread from './ManageBread';

const StoreInformation: React.FC = () => {
  return (
    <Grid flex={1} ml={3}>
      <TodayBread />
      <ManageBread />
    </Grid>
  );
};

export default StoreInformation;
