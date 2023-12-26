import { Box, Grid, Typography } from '@mui/material';
import TodayBread from './TodayBread';
import ManageBread from './ManageBread';
import AddCircleOutlineIcon from '@mui/icons-material/AddCircleOutline';
import { Link } from 'react-router-dom';

type MyStoreInfoProps = {
  isStore?: boolean;
};

const MyStoreInfo: React.FC<MyStoreInfoProps> = ({ isStore }) => {
  return (
    <Grid flex={1} ml={3} position="relative">
      {isStore && (
        <Link to={`/new/store`}>
          <Box
            sx={{
              width: '100%',
              height: '100%',
              position: 'absolute',
              background: 'rgba(255,255,255,0.93)',
              zIndex: 999,
              display: 'flex',
              justifyContent: 'center',
              alignItems: 'center',
              cursor: 'pointer',
              transition: '0.15s ease-in-out',
              '&:hover': {
                background: 'rgba(200, 200, 200, 0.93)',
              },
            }}
          >
            <Box sx={{ textAlign: 'center' }}>
              <AddCircleOutlineIcon sx={{ fontSize: 200 }} />
              <Typography fontSize={20} fontWeight={600}>
                등록된 빵집이 없습니다.
              </Typography>
              <Typography fontSize={20} fontWeight={600}>
                빵집을 등록하세요.
              </Typography>
            </Box>
          </Box>
        </Link>
      )}

      <Box>
        <TodayBread />
        <ManageBread />
      </Box>
    </Grid>
  );
};

export default MyStoreInfo;
