import { Box, Container, Grid, TextField, Typography } from '@mui/material';
import { useState } from 'react';
import { AuthButton } from '../components/Login/AuthButton';

export const Login = () => {
  const [isError, setIsError] = useState<boolean>(false);
  const [inputEmail, setInputEmail] = useState<string>('');

  const inputHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    e.preventDefault();
    setInputEmail(e.target.value);
    const confirmEmail: boolean = /^[\w+_]\w+@\w+\.\w+/.test(inputEmail);
    console.log(confirmEmail);
    setIsError(!confirmEmail);
  };

  return (
    <Container
      sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}
      component={'form'}
    >
      <Typography variant="h4" sx={{ fontWeight: 600 }} mt={10} mb={10}>
        로그인
      </Typography>
      <Box style={{ width: 400 }}>
        <Grid sx={{ height: 125 }}>
          <Typography variant="subtitle1" mb={0.5}>
            이메일
          </Typography>
          <TextField
            onChange={inputHandler}
            error={isError}
            fullWidth
            placeholder="이메일 입력"
            helperText={isError && '이메일 형식을 맞춰주세요.'}
          />
        </Grid>
        <Grid sx={{ height: 125 }}>
          <Typography variant="subtitle1" mb={0.5}>
            비밀번호
          </Typography>
          <TextField fullWidth placeholder="비밀번호 입력" />
        </Grid>
        <Grid mt={3}>
          <AuthButton text="로그인" />
        </Grid>
      </Box>
    </Container>
  );
};