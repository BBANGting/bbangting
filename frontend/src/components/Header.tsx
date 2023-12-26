import { AccountCircle } from '@mui/icons-material';
import {
  AppBar,
  Box,
  Container,
  IconButton,
  MenuItem,
  ThemeProvider,
  Toolbar,
  Typography,
} from '@mui/material';
import { ReactElement, useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { checkToken } from '../apis/api/auth';
import { headerTheme } from '../styles/themes';

const leftOptions = [
  {
    name: '오픈 예정',
    link: '/commingsoon',
  },
  {
    name: '스토어',
    link: '/store',
  },
];

const rightOptions = [
  {
    name: '로그인',
    link: '/login',
  },
  {
    name: '회원가입',
    link: '/signup',
  },
];

export const Header = (): ReactElement => {
  const [isLogin, setIsLogin] = useState<boolean>(false);

  useEffect(() => {
    checkToken()
      .then(() => setIsLogin(true))
      .catch(() => setIsLogin(false));
  }, []);

  return (
    <ThemeProvider theme={headerTheme}>
      <Box sx={{ flexGrow: 1 }}>
        <AppBar position="static" color="header">
          <Container disableGutters sx={{ minHeight: 64 }}>
            <Toolbar
              sx={{ maxWidth: 1200, flexGrow: 1, minHeight: 64 }}
              disableGutters
            >
              <Link to={`/`}>
                <Typography
                  variant="h6"
                  component={'div'}
                  sx={{ flexGrow: 0, fontWeight: 600, fontSize: 24, mr: 5 }}
                >
                  BBANGTING
                </Typography>
              </Link>
              <Box sx={{ flexGrow: 1, display: 'flex' }}>
                {leftOptions.map((menu, idx) => (
                  <Link to={menu.link} key={idx}>
                    <MenuItem sx={{ borderRadius: 5 }}>
                      <Typography variant="h6" sx={{ fontWeight: 400 }}>
                        {menu.name}
                      </Typography>
                    </MenuItem>
                  </Link>
                ))}
              </Box>
              {!isLogin ? (
                <>
                  {rightOptions.map((menu, idx) => (
                    <Link to={menu.link} key={idx}>
                      <MenuItem sx={{ borderRadius: 5 }}>
                        <Typography variant="h6" sx={{ fontWeight: 400 }}>
                          {menu.name}
                        </Typography>
                      </MenuItem>
                    </Link>
                  ))}
                </>
              ) : (
                <>
                  <Link to={`/mypage`}>
                    <IconButton
                      size="large"
                      aria-label="menu"
                      aria-controls="menu-appbar"
                      aria-haspopup="true"
                      color="accountIcon"
                    >
                      <AccountCircle />
                    </IconButton>
                  </Link>
                </>
              )}
            </Toolbar>
          </Container>
        </AppBar>
      </Box>
    </ThemeProvider>
  );
};
