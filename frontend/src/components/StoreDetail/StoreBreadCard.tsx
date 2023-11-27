import { Box, Grid, Typography } from '@mui/material';

type StoreBreadCardProps = {
  breadImage: string;
  breadName: string;
  tingTime: string;
  tingStatus: string;
};

const extractTime = (tingTime: string) => {
  const date = new Date(tingTime);
  const hours = date.getHours();
  const minutes = date.getMinutes();
  return `${hours}:${minutes < 10 ? `0${minutes}` : minutes}`;
};

const StoreBreadCard: React.FC<StoreBreadCardProps> = ({
  breadImage,
  breadName,
  tingStatus,
  tingTime,
}) => {
  return (
    <Grid item xs={12} sm={6} md={4}>
      <Box
        padding={3}
        display="flex"
        flexDirection="column"
        alignItems={'center'}
      >
        <Box position="relative" width={280}>
          <img
            src={breadImage}
            alt="빵 사진"
            width={'100%'}
            style={{
              borderRadius: 10,
              filter: tingStatus === 'N' ? 'brightness(0.6)' : 'none',
            }}
            height={200}
          />
          {tingStatus === 'Y' ? (
            <Box
              position="absolute"
              right={0}
              top={0}
              sx={{
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center',
                background: '#8e8e8e',
                opacity: 0.7,
                minWidth: 80,
                height: 40,
                color: '#fff',
                borderRadius: '0 10px 0 0 ',
                padding: '0 5px',
              }}
            >
              <Typography fontWeight={600}>
                오픈 {extractTime(tingTime)}
              </Typography>
            </Box>
          ) : (
            <Typography
              sx={{
                position: 'absolute',
                top: '50%',
                left: '50%',
                transform: 'translate(-50%, -50%)',
                color: '#fff',
              }}
              variant="h5"
            >
              품절
            </Typography>
          )}
        </Box>

        <Typography fontSize={20} mt={3}>
          {breadName}
        </Typography>
      </Box>
    </Grid>
  );
};

export default StoreBreadCard;
