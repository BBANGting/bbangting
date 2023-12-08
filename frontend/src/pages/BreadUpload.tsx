import { Button, Container, Grid, TextField, Typography } from '@mui/material';
import axios from 'axios';
import { useRef } from 'react';

const BreadUpload = () => {
  const logoImg = useRef<HTMLInputElement>(null);
  const descImg = useRef<HTMLInputElement>(null);
  const name = useRef<HTMLInputElement>(null);
  const price = useRef<HTMLInputElement>(null);
  const stock = useRef<HTMLInputElement>(null);
  const tingDate = useRef<HTMLInputElement>(null);

  const clickHandler = () => {
    const formdata = new FormData();
    formdata.append('imageFile', logoImg.current.files[0]);
    formdata.append('imageFile', descImg.current.files[0]);

    const jsonBody = {
      storeId: 1,
      breadName: name.current.value,
      description: '맛난 빵',
      price: price.current.value,
      tingDateTime: tingDate.current.value,
      maxTingNum: stock.current.value,
      tingStatus: 'Y',
    };

    const headers = {
      'Content-Type': 'multipart/form-data',
    };

    formdata.append(
      'requestDto',
      new Blob([JSON.stringify(jsonBody)], { type: 'application/json' }),
    );

    axios
      .post(`http://localhost:8080/myStore/bread/new`, formdata, { headers })
      .then(res => console.log(res))
      .catch(error => console.error(error));
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
          빵팅 등록
        </Typography>
        <Grid display={'flex'}>
          <Grid item width={300} mr={10}>
            <Grid>
              <Button fullWidth component="label" variant="contained">
                빵 사진 업로드
                <input type="file" hidden ref={logoImg} />
              </Button>
            </Grid>
            <Grid>
              <Button fullWidth component="label" variant="contained">
                빵 설명 사진 업로드
                <input type="file" hidden ref={descImg} />
              </Button>
            </Grid>
          </Grid>
          <Grid item width={400}>
            <Grid sx={{ height: 125 }}>
              <Typography variant="subtitle1" mb={0.5}>
                빵 이름
              </Typography>
              <TextField
                fullWidth
                placeholder="빵 이름 입력"
                type="text"
                inputProps={{ ref: name }}
              />
            </Grid>
            <Grid sx={{ height: 125 }}>
              <Typography variant="subtitle1" mb={0.5}>
                가격
              </Typography>
              <TextField
                fullWidth
                placeholder="가격 입력"
                type="text"
                inputProps={{ ref: price }}
              />
            </Grid>
            <Grid sx={{ height: 125 }}>
              <Typography variant="subtitle1" mb={0.5}>
                재고
              </Typography>
              <TextField
                fullWidth
                placeholder="재고 입력"
                type="number"
                inputProps={{ ref: stock }}
              />
            </Grid>
            <Grid sx={{ height: 125 }}>
              <Typography variant="subtitle1" mb={0.5}>
                빵팅 날짜
              </Typography>
              <TextField
                fullWidth
                placeholder="재고 입력"
                type="datetime-local"
                inputProps={{ ref: tingDate }}
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

export default BreadUpload;
