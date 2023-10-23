import { ArrowBackIos, ArrowForwardIos } from '@mui/icons-material';
import { Box, Typography } from '@mui/material';

export const MainBanner = () => {
  //   const width = useMediaQuery('(max-width:1440px)');

  return (
    <Box
      sx={{
        height: 450,
        bgcolor: '#D8CBAC',
        display: 'flex',
        padding: 2,
        justifyContent: 'center',
      }}
    >
      <Box
        sx={{
          display: 'flex',
          width: 1440,
        }}
      >
        <Box sx={{ alignSelf: 'flex-end' }}>
          <Typography variant="h6" sx={{ color: '#ffffff', lineHeight: 1.2 }}>
            성수 빵집
          </Typography>
          <Typography
            variant="h6"
            sx={{
              color: '#ffffff',
              borderBottom: '2px solid',
            }}
          >
            티켓팅 바로가기
          </Typography>
        </Box>
        <Box sx={{ marginLeft: 'auto' }}>
          <img
            src="/imgs/bread.png"
            alt="bread"
            style={{ width: 400, height: 400, right: 0 }}
          />
        </Box>
        <Box sx={{ alignSelf: 'flex-end', display: 'flex' }}>
          <Box sx={buttonStyle}>
            <ArrowBackIos sx={{ color: '#ffffff', marginLeft: 1 }} />
          </Box>
          <Box sx={buttonStyle}>
            <ArrowForwardIos sx={{ color: '#ffffff' }} />
          </Box>
        </Box>
      </Box>
    </Box>
  );
};

const buttonStyle = {
  width: 40,
  height: 40,
  bgcolor: '#838383',
  display: 'flex',
  justifyContent: 'center',
  alignItems: 'center',
  '&:hover': {
    bgcolor: '#444444',
  },
  cursor: 'pointer',
};
