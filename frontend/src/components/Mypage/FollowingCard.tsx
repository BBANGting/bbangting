import { Grid, IconButton, Typography } from '@mui/material';
import ClearIcon from '@mui/icons-material/Clear';

type FollowingCardProps = {
  img: string;
  name: string;
};

const FollowingCard: React.FC<FollowingCardProps> = ({ img, name }) => {
  return (
    <Grid
      item
      xs={4}
      sx={{
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        height: 270,
      }}
    >
      <Grid
        container
        width={220}
        height={220}
        boxShadow={'1px 1px 5px 0px rgba(0, 0, 0, 0.05)'}
        borderRadius={5}
        justifyContent="center"
        alignItems="center"
        position="relative"
      >
        <img src={img} width={124} height={124} style={{ borderRadius: 70 }} />
        <Typography>{name}</Typography>
        <IconButton
          sx={{ position: 'absolute', top: 0, right: 0, color: '#aaaaaa' }}
        >
          <ClearIcon />
        </IconButton>
      </Grid>
    </Grid>
  );
};

export default FollowingCard;
