import { Grid } from '@mui/material';

type StoreBannerProps = {
  storeImage: string;
  storeLogo: string;
};

const StoreBanner: React.FC<StoreBannerProps> = ({ storeImage, storeLogo }) => {
  return (
    <Grid position="relative" width="100%" maxHeight={372}>
      <img
        src={storeImage}
        alt="스토어 배경"
        width="100%"
        style={{ objectFit: 'cover', minHeight: 270, height: 370 }}
      />
      <img
        src={storeLogo}
        alt="스토어 로고"
        width={130}
        height={130}
        style={{
          position: 'absolute',
          objectFit: 'cover',
          bottom: 20,
          left: 20,
          borderRadius: 999,
        }}
      />
    </Grid>
  );
};

export default StoreBanner;
