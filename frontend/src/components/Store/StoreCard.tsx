import { Grid, Typography } from '@mui/material';

type StoreCardProps = {
  img: string;
  name: string;
};
const StoreCard = ({ img, name }: StoreCardProps) => {
  return (
    <Grid
      item
      xs={3}
      container
      direction={'column'}
      alignItems={'center'}
      position={'relative'}
      mt={10}
    >
      <img
        src={img}
        alt="..."
        style={{ width: 180, height: 180, borderRadius: 100 }}
      />
      <Typography mt={3}>{name}</Typography>
    </Grid>
  );
};

export default StoreCard;
