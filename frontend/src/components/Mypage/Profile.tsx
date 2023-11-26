import { Grid, IconButton, Typography } from '@mui/material';
import InfoTitle from './InfoTitle';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import EditIcon from '@mui/icons-material/Edit';

type ProfileProps = {
  img: string;
  nickname: string;
  name?: string;
  email?: string;
  warning?: number;
  follower?: number;
};

const Profile: React.FC<ProfileProps> = ({
  img,
  nickname,
  name,
  email,
  warning,
  follower,
}: ProfileProps) => {
  console.log(follower);
  return (
    <Grid container width={350} mr={3}>
      <Grid container direction="column" alignItems="center">
        <img src={img} alt="" width="300" style={{ borderRadius: 99999 }} />
        <Grid sx={{ display: 'flex', alignItems: 'center' }} mt={6}>
          <InfoTitle>{nickname} 님</InfoTitle>
          {follower && (
            <IconButton sx={{ p: 1, color: '#dddddd' }}>
              <EditIcon />
            </IconButton>
          )}
        </Grid>
        {!follower ? (
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
        ) : (
          <Grid
            sx={{ width: '100%', mt: 2 }}
            container
            justifyContent="center"
            alignItems={'center'}
          >
            <Typography>
              팔로워 <strong>{follower}</strong>
            </Typography>
            <IconButton sx={{ padding: 0.5 }}>
              <ChevronRightIcon sx={{ height: '18px', width: '18px' }} />
            </IconButton>
          </Grid>
        )}
      </Grid>
    </Grid>
  );
};

export default Profile;
