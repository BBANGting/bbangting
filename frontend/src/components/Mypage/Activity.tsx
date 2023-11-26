import { Grid } from '@mui/material';
import InfoTitle from './InfoTitle';
import AccessTimeIcon from '@mui/icons-material/AccessTime';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import AttachMoneyIcon from '@mui/icons-material/AttachMoney';
import StarIcon from '@mui/icons-material/Star';

const Activity = () => {
  return (
    <>
      <InfoTitle>나의 활동</InfoTitle>
      <Grid container justifyContent="space-between">
        <Grid
          container
          width={360}
          height={46}
          alignItems="center"
          fontSize={18}
          borderBottom={'1px solid #eaeaea'}
          mt={2}
        >
          <AccessTimeIcon sx={{ color: '#969696', mr: 1 }} />
          구매 내역
          <ChevronRightIcon sx={{ color: '#d6d6d6', ml: 'auto' }} />
        </Grid>
        <Grid
          container
          width={360}
          height={46}
          alignItems="center"
          fontSize={18}
          borderBottom={'1px solid #eaeaea'}
          mt={2}
        >
          <AttachMoneyIcon sx={{ color: '#969696', mr: 1 }} />
          결제 수단
          <ChevronRightIcon sx={{ color: '#d6d6d6', ml: 'auto' }} />
        </Grid>
        <Grid
          container
          width={360}
          height={46}
          alignItems="center"
          fontSize={18}
          borderBottom={'1px solid #eaeaea'}
          mt={2}
        >
          <StarIcon sx={{ color: '#969696', mr: 1 }} />
          리뷰 관리
          <ChevronRightIcon sx={{ color: '#d6d6d6', ml: 'auto' }} />
        </Grid>
      </Grid>
    </>
  );
};

export default Activity;
