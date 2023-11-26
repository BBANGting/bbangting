import { Grid, Typography } from '@mui/material';

type BannerButtonProps = {
  text: string;
  isStorePage: boolean;
};

const BannerButton: React.FC<BannerButtonProps> = ({ text, isStorePage }) => {
  return (
    <Grid
      container
      justifyContent={'center'}
      alignItems={'center'}
      width={64}
      height={34}
      borderRadius="10px 10px 0 0"
      sx={{
        background: isStorePage ? '#ffffff' : '#e3e3e3',
        color: isStorePage ? '#DB9662' : '#727272',
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
