import { createTheme } from '@mui/material';

export const theme = createTheme({
  palette: {
    button: {
      main: '#db9662',
      light: '#fdb075',
      dark: '#cb8b59',
      contrastText: '#fff',
    },
  },
});

export const headerTheme = createTheme({
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
