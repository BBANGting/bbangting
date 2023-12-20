import { Grid } from '@mui/material';

type BreadInfoType = {
  breadDetailImg: string;
};

const BreadInfo: React.FC<BreadInfoType> = ({ breadDetailImg }) => {
  console.log(breadDetailImg);
  return (
    <Grid sx={{ textAlign: 'center' }}>
      <img
        src={breadDetailImg}
        alt="빵 상세 정보"
        style={{
          maxWidth: 1152,
        }}
      />
    </Grid>
  );
};

export default BreadInfo;
