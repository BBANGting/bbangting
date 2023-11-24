import { Box, Grid } from '@mui/material';
import BannerButton from './BannerButton';
import { useState } from 'react';
import { Link } from 'react-router-dom';

const StoreBanner: React.FC = () => {
  const [isClick, setIsClick] = useState<boolean>(true);

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
            <BannerButton isClick={isClick} text="고객" />
          </Link>
          <Link to={`/mystorepage`}>
            <BannerButton isClick={!isClick} text="빵집" />
          </Link>
        </Grid>
      </Grid>
    </Box>
  );
};

export default StoreBanner;
