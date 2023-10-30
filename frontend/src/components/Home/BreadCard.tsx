import { Box, Grid, Typography } from '@mui/material';

type BreadCardProps = {
  store: string;
  name: string;
  openTime: string;
  img: string;
};

export const BreadCard = ({ store, name, openTime, img }: BreadCardProps) => {
  return (
    <Grid
      item
      xs={4}
      display={'flex'}
      direction="column"
      alignItems={'center'}
      mb={5}
    >
      <Box
        position="relative"
        sx={{ width: 320, height: 250, marginBottom: 2 }}
      >
        <img
          src={img}
          alt=".."
          style={{ width: '100%', height: '100%', borderRadius: 10 }}
        />
        <Box
          position="absolute"
          display="flex"
          top={0}
          right={0}
          sx={{
            bgcolor: 'rgba(142, 142, 142, 0.67)',
            width: 95,
            height: 40,
            justifyContent: 'center',
            alignItems: 'center',
            borderTopRightRadius: 10,
          }}
        >
          <Typography sx={{ fontWeight: 600, color: '#fff' }}>
            오픈 {openTime}
          </Typography>
        </Box>
      </Box>
      <Typography sx={{ fontSize: 20 }}>
        [{store}] {name}
      </Typography>
    </Grid>
  );
};
