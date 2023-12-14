import {
  Button,
  ButtonGroup,
  Grid,
  ThemeProvider,
  Typography,
  createTheme,
} from '@mui/material';
import { useState } from 'react';
import KeyboardArrowUpIcon from '@mui/icons-material/KeyboardArrowUp';
import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown';
import { TBread } from '../../types';

type PurchaseProps = {
  bread: TBread;
};

const theme = createTheme({
  palette: {
    button: {
      main: '#db9662',
      light: '#fdb075',
      dark: '#cb8b59',
      contrastText: '#fff',
    },
  },
});

//FEAT: 코드 리팩토링 필요
const Purchase: React.FC<PurchaseProps> = ({ bread }) => {
  const [count, setCount] = useState<number>(0);

  return (
    <ThemeProvider theme={theme}>
      <Grid container mt={5}>
        <Grid item xs={12} sm={6}>
          <img
            src={bread.imgUrl}
            alt="빵 사진"
            width={'100%'}
            style={{ maxWidth: 530, maxHeight: 350 }}
          />
        </Grid>
        <Grid
          sx={{ display: 'flex' }}
          direction={'column'}
          item
          xs={12}
          sm={6}
          maxHeight={350}
        >
          <Typography fontSize={30} fontWeight={600}>
            [돌체테리아 성수점]{bread.breadName}
          </Typography>
          <Typography>별점?</Typography>
          <Typography fontSize={26} mt={'10%'}>
            <span style={{ color: '#db9669', fontSize: 30 }}>
              {bread.price.toLocaleString()}
            </span>{' '}
            원
          </Typography>
          <Typography mt={'10%'}>재고: {bread.stock}개</Typography>
          <Grid container mt={'auto'}>
            <Grid item sx={{ display: 'flex' }} xs={4}>
              <input
                style={{
                  width: 70,
                  height: 50,
                  textAlign: 'center',
                  fontSize: 20,
                  border: '1px solid #bbbbbb',
                  borderRadius: '3px 0 0 3px',
                }}
                value={count}
              />
              <ButtonGroup orientation="vertical">
                <Button
                  sx={{
                    border: '1px solid #bbbbbb',
                    borderLeft: 0,
                    borderRadius: '0 3px 0 0',
                    p: 0,
                    height: 28,
                  }}
                  color="button"
                  onClick={() => {
                    setCount(count => count + 1);
                  }}
                >
                  <KeyboardArrowUpIcon sx={{ color: '#bbbbbb' }} />
                </Button>
                <Button
                  sx={{
                    border: '1px solid #bbbbbb',
                    borderLeft: 0,
                    borderRadius: '0 0 3px 0',
                    p: 0,
                    height: 27,
                  }}
                  color="button"
                  onClick={() => {
                    setCount(count => count - 1);
                  }}
                  disabled={count === 0}
                >
                  <KeyboardArrowDownIcon sx={{ color: '#bbbbbb' }} />
                </Button>
              </ButtonGroup>
            </Grid>
            <Grid item xs={8} ml={'auto'} width={'100%'}>
              <Button
                variant="contained"
                color="button"
                sx={{ height: '100%', width: '100%' }}
              >
                <Typography fontWeight={600} fontSize={20}>
                  구매하기
                </Typography>
              </Button>
            </Grid>
          </Grid>
        </Grid>
      </Grid>
    </ThemeProvider>
  );
};

export default Purchase;
