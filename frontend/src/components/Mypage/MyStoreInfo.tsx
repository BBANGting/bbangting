import { Grid } from '@mui/material';
import TodayBread from './TodayBread';
import ManageBread from './ManageBread';

const MyStoreInfo: React.FC = () => {
  return (
    <Grid flex={1} ml={3}>
      <TodayBread />
      <ManageBread />
    </Grid>
  );
};

export default MyStoreInfo;
