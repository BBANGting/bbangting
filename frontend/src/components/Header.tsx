import { AccountCircle } from '@mui/icons-material';
import {
  AppBar,
  Box,
  IconButton,
  MenuItem,
  ThemeProvider,
  Toolbar,
  Typography,
  createTheme,
  useMediaQuery,
} from '@mui/material';
import { ReactElement, useState } from 'react';
import { Link } from 'react-router-dom';

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
          <Toolbar sx={{ minWidth: width ? 1080 : 1440 }} disableGutters>
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
              {leftOptions.map(menu => (
                <Link to={menu.link}>
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
                {rightOptions.map(menu => (
                  <Link to={menu.link}>
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
