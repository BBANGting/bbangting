import { AccountCircle } from '@mui/icons-material';
import {
  AppBar,
  Box,
  IconButton,
  ThemeProvider,
  Toolbar,
  Typography,
  createTheme,
  useMediaQuery,
} from '@mui/material';
import { ReactElement, useState } from 'react';

const theme = createTheme({
  palette: {
    header: {
      main: '#ffffff',
      contrastText: '#464643',
    },
    accountIcon: {
      main: '#000000',
    },
  },
});

export const Header = (): ReactElement => {
  const [isLogin, setIsLogin] = useState<boolean>(false);

  const width = useMediaQuery('(max-width:1440px)');

  return (
    <ThemeProvider theme={theme}>
      <Box sx={{ flexGrow: 1 }}>
        <AppBar position="static" color="header" sx={{ placeItems: 'center' }}>
          <Toolbar sx={{ minWidth: width ? 1000 : 1440 }}>
            <Typography
              variant="h6"
              component={'div'}
              sx={{ flexGrow: 1, fontWeight: 600 }}
            >
              BBANGTING
            </Typography>
            {!isLogin ? (
              <>
                <Typography
                  variant="h6"
                  component={'div'}
                  sx={{ flexGrow: 0, fontWeight: 400 }}
                  mr={3}
                >
                  로그인
                </Typography>
                <Typography
                  variant="h6"
                  component={'div'}
                  sx={{ flexGrow: 0, fontWeight: 400 }}
                >
                  회원가입
                </Typography>
              </>
            ) : (
              <>
                <IconButton
                  size="large"
                  aria-label="menu"
                  aria-controls="menu-appbar"
                  aria-haspopup="true"
                  onClick={() => {
                    setIsLogin(false);
                  }}
                  color="accountIcon"
                >
                  <AccountCircle />
                </IconButton>
              </>
            )}
          </Toolbar>
        </AppBar>
      </Box>
    </ThemeProvider>
  );
};
