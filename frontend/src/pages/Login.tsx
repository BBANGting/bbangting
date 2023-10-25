import { Box, Container, Typography } from '@mui/material';
import { InputBox } from '../components/Login/InputBox';

export const Login = () => {
  return (
    <Container
      sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}
    >
      <Typography variant="h4" sx={{ fontWeight: 600 }} mt={10} mb={10}>
        로그인
      </Typography>
      <Box style={{ width: 400 }}>
        <InputBox name="이메일" holder="이메일 입력" />
        <InputBox name="비밀번호" holder="비밀번호 입력" type="password" />
      </Box>
    </Container>
  );
};
