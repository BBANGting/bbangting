import { Grid } from '@mui/material';
import Activity from './Activity';
import Following from './Following';

const Information: React.FC = () => {
  return (
    <Grid flex={1} ml={3}>
      <Activity />
      <Following />
    </Grid>
  );
};

export default Information;
