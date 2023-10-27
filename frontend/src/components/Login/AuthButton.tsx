import { Button, ThemeProvider, Typography, createTheme } from '@mui/material';

type AuthButtonProps = {
  text: string;
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

export const AuthButton = ({ text }: AuthButtonProps) => {
  return (
    <ThemeProvider theme={theme}>
      <Button variant="contained" fullWidth sx={{ height: 50 }} color="button">
        <Typography variant="h6">{text}</Typography>
      </Button>
    </ThemeProvider>
  );
};
