import { Grid } from '@mui/material';
import InfoTitle from './InfoTitle';
import FollowingCard from './FollowingCard';

const Following = () => {
  return (
    <>
      <InfoTitle sx={{ mt: 15 }}>팔로잉</InfoTitle>
      <Grid container justifyContent="flex-start">
        <FollowingCard img="/imgs/touslesjours.png" name="뚜레쥬르 성수점" />
        <FollowingCard img="/imgs/touslesjours.png" name="뚜레쥬르 성수점" />
        <FollowingCard img="/imgs/touslesjours.png" name="뚜레쥬르 성수점" />
        <FollowingCard img="/imgs/touslesjours.png" name="뚜레쥬르 성수점" />
        <FollowingCard img="/imgs/touslesjours.png" name="뚜레쥬르 성수점" />
      </Grid>
    </>
  );
};

export default Following;
