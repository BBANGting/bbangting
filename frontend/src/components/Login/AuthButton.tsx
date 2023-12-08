import { Button, ThemeProvider, Typography, createTheme } from '@mui/material';

type AuthButtonProps = {
  text: string;
  onClick: React.MouseEventHandler<HTMLButtonElement>;
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

export const AuthButton = ({ text, onClick }: AuthButtonProps) => {
  return (
    <ThemeProvider theme={theme}>
      <Button
        variant="contained"
        fullWidth
        sx={{ height: 50 }}
        color="button"
        onClick={onClick}
      >
        <Typography variant="h6">{text}</Typography>
      </Button>
    </ThemeProvider>
  );
};
