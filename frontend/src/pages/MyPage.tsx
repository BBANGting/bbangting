import { Box, Container, Grid } from '@mui/material';
import Profile from '../components/Mypage/Profile';
import Information from '../components/Mypage/Information';

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
      <Container disableGutters>
        <Grid container mt={10} direction={'row'}>
          <Profile
            nickname="비누"
            name="김진우"
            email="rlawlsdn316@gmail.com"
            warning={2}
          />
          <Information />
        </Grid>
      </Container>
    </>
  );
};
