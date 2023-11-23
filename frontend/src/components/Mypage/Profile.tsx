import { Grid, Typography } from '@mui/material';

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
    <Grid container width={350} mt={10}>
      <Grid container direction="column" alignItems="center">
        <img
          src="/imgs/profile.JPG"
          alt=""
          width="300"
          style={{ borderRadius: 99999 }}
        />
        <Typography variant="h5" sx={{ mt: 6, fontWeight: 700 }}>
          {nickname} 님
        </Typography>
        <Grid sx={{ width: '100%', mt: 5 }} container>
          <div>
            <Typography style={{ lineHeight: '3' }}>이름</Typography>
            <Typography style={{ lineHeight: '3' }}>이메일</Typography>
            <Typography style={{ lineHeight: '3' }}>경고</Typography>
          </div>
          <div style={{ marginLeft: 50 }}>
            <Typography style={{ lineHeight: '3', fontWeight: '700' }}>
              {name}
            </Typography>
            <Typography style={{ lineHeight: '3', fontWeight: '700' }}>
              {email}
            </Typography>
            <Typography
              style={{ lineHeight: '3', fontWeight: '700', color: '#DB9662' }}
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
