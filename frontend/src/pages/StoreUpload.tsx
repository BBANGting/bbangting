import { Button, Container, Grid, TextField, Typography } from '@mui/material';
import { useRef } from 'react';
import { newMyStore } from '../apis/api/mystore';
import { useRecoilValue } from 'recoil';
import { userState } from '../store/user';

const StoreUpload = () => {
  const logoImg = useRef<HTMLInputElement>(null);
  const descImg = useRef<HTMLInputElement>(null);
  const name = useRef<HTMLInputElement>(null);
  const location = useRef<HTMLInputElement>(null);
  const description = useRef<HTMLInputElement>(null);

  const { userId } = useRecoilValue(userState);

  const clickHandler = () => {
    const formdata = new FormData();
    formdata.append('imageFile', logoImg.current.files[0]);
    formdata.append('imageFile', descImg.current.files[0]);

    const jsonBody = {
      userId,
      storeName: name.current?.value,
      description: '개쩌는 빵 판매중',
      location: location.current?.value,
    };

    formdata.append(
      'requestDto',
      new Blob([JSON.stringify(jsonBody)], { type: 'application/json' }),
    );

    newMyStore(formdata)
      .then(() => alert('가게 등록 성공!'))
      .catch(err => {
        alert('실패!');
        console.log(err);
      });
  };

  return (
    <>
      <Container
        sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}
      >
        <Typography
          variant="h5"
          mt={5}
          sx={{ fontWeight: 600, marginBottom: 5 }}
        >
          스토어 등록
        </Typography>
        <Grid display={'flex'}>
          <Grid item width={300} mr={10}>
            <Grid>
              <Button fullWidth component="label" variant="contained">
                가게 로고 업로드
                <input type="file" hidden ref={logoImg} />
              </Button>
            </Grid>
            <Grid>
              <Button fullWidth component="label" variant="contained">
                가게 배경사진 업로드
                <input type="file" hidden ref={descImg} />
              </Button>
            </Grid>
          </Grid>
          <Grid item width={400}>
            <Grid sx={{ height: 125 }}>
              <Typography variant="subtitle1" mb={0.5}>
                가게 이름
              </Typography>
              <TextField
                fullWidth
                placeholder="가게 이름 입력"
                type="text"
                inputProps={{ ref: name }}
              />
            </Grid>
            <Grid sx={{ height: 125 }}>
              <Typography variant="subtitle1" mb={0.5}>
                위치
              </Typography>
              <TextField
                fullWidth
                placeholder="위치 입력"
                type="text"
                inputProps={{ ref: location }}
              />
            </Grid>
            <Grid sx={{ height: 125 }}>
              <Typography variant="subtitle1" mb={0.5}>
                설명
              </Typography>
              <TextField
                fullWidth
                placeholder="설명 입력"
                type="text"
                inputProps={{ ref: description }}
              />
            </Grid>
          </Grid>
        </Grid>
        <Button variant="contained" onClick={clickHandler}>
          제출
        </Button>
      </Container>
    </>
  );
};

export default StoreUpload;
