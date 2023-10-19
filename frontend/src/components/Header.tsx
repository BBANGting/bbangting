import {
  AppBar,
  Box,
  ThemeProvider,
  Toolbar,
  Typography,
  createTheme,
} from '@mui/material';
import { ReactElement } from 'react';

const theme = createTheme({
  palette: {
    header: {
      main: '#ffffff',
      contrastText: '#242105',
    },
  },
});

export const Header = (): ReactElement => {
  return (
    <ThemeProvider theme={theme}>
      <Box sx={{ flexGrow: 1 }}>
        <AppBar position="static" color="header" sx={{ placeItems: 'center' }}>
          <Toolbar sx={{ width: 1440 }}>
            <Typography
              variant="h6"
              component={'div'}
              sx={{ flexGrow: 1, fontWeight: 600 }}
            >
              BBANGTING
            </Typography>
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
          </Toolbar>
        </AppBar>
      </Box>
    </ThemeProvider>
  );
};

declare module '@mui/material/styles' {
  interface Palette {
    header: Palette['primary'];
  }
  interface PaletteOptions {
    header?: PaletteOptions['primary'];
  }
}

declare module '@mui/material/AppBar' {
  interface AppBarPropsColorOverrides {
    header: true;
  }
}
