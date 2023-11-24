import { Box, Grid } from '@mui/material';
import BannerButton from './BannerButton';
import { Link, useLocation } from 'react-router-dom';

const StoreBanner: React.FC = () => {
  const location = useLocation();
  const isStorePage = location.pathname === '/mystorepage';
  console.log(isStorePage);

  return (
    <Box
      sx={{
        width: '100%',
        background: '#DB9662',
        height: 70,
      }}
    >
      <Grid width={1200} height={'100%'} container margin={'0 auto'}>
        <Grid sx={{ display: 'flex' }} marginTop="auto" marginLeft="auto">
          <Link to={`/mypage`}>
            <BannerButton isStorePage={!isStorePage} text="고객" />
          </Link>
          <Link to={`/mystorepage`}>
            <BannerButton isStorePage={isStorePage} text="빵집" />
          </Link>
        </Grid>
      </Grid>
    </Box>
  );
};

export default StoreBanner;
