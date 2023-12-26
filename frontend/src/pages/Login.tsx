import { Box, Container, Grid, TextField, Typography } from '@mui/material';
import { useState } from 'react';
import { userLogin } from '../apis/api/auth';
import { useNavigate } from 'react-router-dom';
import { useSetRecoilState } from 'recoil';
import { tokenState } from '../store/auth';
import { userState } from '../store/user';
import ColorButton from '../components/common/ColorButton';

export const Login = () => {
  const [isError, setIsError] = useState<boolean>(false);
  const [inputEmail, setInputEmail] = useState<string>('');
  const [inputPassword, setInputPassword] = useState<string>('');

  const setUserState = useSetRecoilState(userState);

  const setToken = useSetRecoilState(tokenState);

  const navigate = useNavigate();

  const clickHandler = async () => {
    const formData = new FormData();
    formData.append('email', inputEmail);
    formData.append('password', inputPassword);

    userLogin(formData)
      .then(res => {
        setUserState(res.data);
        const accessToken = res.headers.access_token;
        const refreshToken = res.headers.refresh_token;
        localStorage.setItem('access-token', accessToken);
        localStorage.setItem('refresh-token', refreshToken);
        setToken(accessToken);
        navigate('/');
      })
      .catch(err => {
        alert('이메일 혹은 비밀번호를 확인하세요');
        console.log(err);
      });
  };

  const inputHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    e.preventDefault();
    setInputEmail(e.target.value);
    const confirmEmail: boolean = /^[\w+_]\w+@\w+\.\w+/.test(e.target.value);
    setIsError(!confirmEmail);
  };

  const passwordInputHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    e.preventDefault();
    setInputPassword(e.target.value);
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
          <TextField
            fullWidth
            placeholder="비밀번호 입력"
            type="password"
            onChange={passwordInputHandler}
          />
        </Grid>
        <Grid mt={3}>
          <ColorButton fullWidth sx={{ height: 50 }} onClick={clickHandler}>
            <Typography variant="h6">로그인</Typography>
          </ColorButton>
        </Grid>
      </Box>
    </Container>
  );
};
