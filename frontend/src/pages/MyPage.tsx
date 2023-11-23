import { Box, Container } from '@mui/material';
import Profile from '../components/Mypage/Profile';

export const MyPage = () => {
  return (
    <>
      <Box
        sx={{
          width: '100%',
          background: '#DB9662',
          height: 70,
        }}
      />
      <Container>
        <Profile
          nickname="비누"
          name="김진우"
          email="rlawlsdn316@gmail.com"
          warning={2}
        />
      </Container>
    </>
  );
};
