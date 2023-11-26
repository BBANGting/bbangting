import { Box, Typography } from '@mui/material';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';

type TodayBreadCardProps = {
  img: string;
  name: string;
  curCount: number;
  maxCount: number;
};

const TodayBreadCard: React.FC<TodayBreadCardProps> = ({
  img,
  name,
  curCount,
  maxCount,
}) => {
  return (
    <Box
      sx={{ display: 'flex', alignItems: 'center' }}
      padding={'20px 0'}
      width={'100%'}
      borderBottom={'1px solid #f1f1f1'}
    >
      <img src={img} alt="bread" width={85} style={{ borderRadius: '10px' }} />
      <Typography ml={3}>
        <span>
          {name} X {curCount}개
        </span>
        <span style={{ color: '#d1d1d1' }}> / {maxCount}개</span>
      </Typography>
      <ChevronRightIcon
        fontSize="large"
        sx={{ color: '#d6d6d6', ml: 'auto' }}
      />
    </Box>
  );
};

export default TodayBreadCard;
