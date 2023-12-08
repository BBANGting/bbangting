import { Box, Container, Grid, TextField, Typography } from '@mui/material';
import { useRef, useState } from 'react';
import { AuthButton } from '../components/Login/AuthButton';
import axios from 'axios';

export const Signup = () => {
  const [isError, setIsError] = useState<boolean>(false);

  const [inputEmail, setInputEmail] = useState<string>('');

  const nameRef = useRef<HTMLInputElement>(null);
  const nicknameRef = useRef<HTMLInputElement>(null);
  const emailRef = useRef<HTMLInputElement>(null);
  const pwdRef = useRef<HTMLInputElement>(null);

  const clickHandler = () => {
    axios
      .post('http://localhost:8080/auth/join', {
        username: nameRef.current.value,
        nickname: nicknameRef.current.value,
        email: emailRef.current.value,
        password: pwdRef.current.value,
      })
      .then(res => console.log(res))
      .catch(err => console.log(err));
  };

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
        회원가입
      </Typography>
      <Box style={{ width: 400 }}>
        <Grid sx={{ height: 125 }}>
          <Typography variant="subtitle1" mb={0.5}>
            이름
          </Typography>
          <TextField fullWidth placeholder="이름 입력" inputRef={nameRef} />
        </Grid>
        <Grid sx={{ height: 125 }}>
          <Typography variant="subtitle1" mb={0.5}>
            닉네임
          </Typography>
          <TextField fullWidth placeholder="이름 입력" inputRef={nicknameRef} />
        </Grid>
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
            inputRef={emailRef}
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
            inputRef={pwdRef}
          />
        </Grid>
        <Grid sx={{ height: 125 }}>
          <Typography variant="subtitle1" mb={0.5}>
            비밀번호 확인
          </Typography>
          <TextField
            fullWidth
            placeholder="비밀번호 확인"
            type="password"
            helperText="비밀번호와 똑같이 입력하세요."
          />
        </Grid>
        <Grid mt={3}>
          <AuthButton text="회원가입 완료" onClick={clickHandler} />
        </Grid>
      </Box>
    </Container>
  );
};
