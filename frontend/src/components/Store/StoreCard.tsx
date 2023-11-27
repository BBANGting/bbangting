import { Grid, Typography } from '@mui/material';
import { useNavigate } from 'react-router-dom';

type StoreCardProps = {
  img: string;
  name: string;
  storeId: number;
};

const StoreCard = ({ storeId, img, name }: StoreCardProps) => {
  const navigate = useNavigate();
  const clickHandler = () => {
    navigate(`/store/${storeId}`);
  };

  return (
    <Grid
      item
      xs={12}
      sm={6}
      md={4}
      lg={3}
      container
      direction={'column'}
      alignItems={'center'}
      position={'relative'}
      p={'40px 0'}
      onClick={clickHandler}
      className="hover"
    >
      <div
        style={{
          width: 180,
          height: 180,
          borderRadius: 100,
          overflow: 'hidden',
        }}
      >
        <img
          src={img}
          alt="..."
          style={{ width: '100%', height: '100%', objectFit: 'cover' }}
        />
      </div>
      <Typography mt={3}>{name}</Typography>
    </Grid>
  );
};

export default StoreCard;
