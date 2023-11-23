import { Box, Grid, IconButton, Input } from '@mui/material';
import SearchIcon from '@mui/icons-material/Search';

const Search: React.FC = () => {
  return (
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
  );
};

export default Search;
