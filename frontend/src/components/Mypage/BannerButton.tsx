import { Grid, Typography } from '@mui/material';

type BannerButtonProps = {
  text: string;
  isClick: boolean;
};

const BannerButton: React.FC<BannerButtonProps> = ({ text, isClick }) => {
  return (
    <Grid
      container
      justifyContent={'center'}
      alignItems={'center'}
      width={64}
      height={34}
      borderRadius="10px 10px 0 0"
      sx={{
        background: isClick ? '#ffffff' : '#e3e3e3',
        color: isClick ? '#DB9662' : '#727272',
        transition: '0.2s ease-in-out',
        cursor: 'pointer',
        '&:hover': {
          scale: '1.05',
        },
      }}
    >
      <Typography>{text}</Typography>
    </Grid>
  );
};

export default BannerButton;
