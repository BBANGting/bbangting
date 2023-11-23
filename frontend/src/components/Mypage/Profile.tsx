import { Grid, Typography } from '@mui/material';
import InfoTitle from './InfoTitle';

type ProfileProps = {
  nickname: string;
  name: string;
  email: string;
  warning: number;
};

const Profile: React.FC<ProfileProps> = ({
  nickname,
  name,
  email,
  warning,
}: ProfileProps) => {
  return (
    <Grid container width={350} mr={3}>
      <Grid container direction="column" alignItems="center">
        <img
          src="/imgs/profile.JPG"
          alt=""
          width="300"
          style={{ borderRadius: 99999 }}
        />
        <InfoTitle sx={{ mt: 6 }}>{nickname} 님</InfoTitle>
        <Grid sx={{ width: '100%', mt: 5 }} container>
          <div>
            <Typography sx={{ lineHeight: '3' }}>이름</Typography>
            <Typography sx={{ lineHeight: '3' }}>이메일</Typography>
            <Typography sx={{ lineHeight: '3' }}>경고</Typography>
          </div>
          <div style={{ marginLeft: 50 }}>
            <Typography sx={{ lineHeight: '3', fontWeight: '700' }}>
              {name}
            </Typography>
            <Typography sx={{ lineHeight: '3', fontWeight: '700' }}>
              {email}
            </Typography>
            <Typography
              sx={{ lineHeight: '3', fontWeight: '700', color: '#DB9662' }}
            >
              {warning} 회
            </Typography>
          </div>
        </Grid>
      </Grid>
    </Grid>
  );
};

export default Profile;
