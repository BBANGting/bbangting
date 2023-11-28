import {
  Button,
  Grid,
  ThemeProvider,
  Typography,
  createTheme,
} from '@mui/material';
import PersonAddAlt1Icon from '@mui/icons-material/PersonAddAlt1';

type StoreInfoProps = {
  store: {
    storeName: string;
    introduction: string;
    location: string;
    follower: number;
  };
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

const StoreInfo: React.FC<StoreInfoProps> = ({ store }) => {
  return (
    <ThemeProvider theme={theme}>
      <Grid container direction="column" mt={5}>
        <Typography variant="h4" fontWeight={'bold'}>
          {store.storeName}
        </Typography>
        <Typography fontStyle={{ color: '#888888' }}>
          {store.location}
        </Typography>
        <Typography margin={'50px 0'}>{store.introduction}</Typography>
        <Button variant="contained" color="button" style={{ height: 50 }}>
          <PersonAddAlt1Icon style={{ marginRight: '10px' }} />
          <Typography>팔로우 {store.follower}</Typography>
        </Button>
      </Grid>
    </ThemeProvider>
  );
};

export default StoreInfo;
