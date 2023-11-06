import {
  Box,
  Container,
  Grid,
  IconButton,
  Input,
  Typography,
} from '@mui/material';
import StarsIcon from '@mui/icons-material/Stars';
import SearchIcon from '@mui/icons-material/Search';

export const Store = () => {
  return (
    <Container fixed style={{ marginTop: 40, paddingLeft: 40 }}>
      <Typography variant="h5" sx={{ fontWeight: 600, marginBottom: 5 }}>
        스토어
      </Typography>
      <Grid display="flex">
        <Grid flexGrow={1} display="flex" alignItems="center">
          <StarsIcon />
          <Typography variant="h6" fontWeight="600" ml={1}>
            RANK
          </Typography>
        </Grid>
        <Grid flexGrow={0}>
          <Box position="relative">
            <Input
              sx={{
                border: 2,
                borderRadius: 2,
                width: 350,
                height: 50,
                borderColor: '#a1a1a1',
                paddingLeft: 2,
                paddingRight: 5,
              }}
              placeholder="스토어를 검색하세요"
              disableUnderline
            />
            <IconButton
              type="button"
              aria-label="search"
              sx={{ position: 'absolute', right: 0, top: 5 }}
            >
              <SearchIcon />
            </IconButton>
          </Box>
        </Grid>
      </Grid>
      <Box mt={4} border={2} height={300} borderRadius={3} color="#DB9662">
        <Grid>
          <img src="/imgs/bbangzip.png" alt="..." />
        </Grid>
      </Box>
    </Container>
  );
};
