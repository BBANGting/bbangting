import { Button, Grid, Typography } from '@mui/material';
import PersonAddAlt1Icon from '@mui/icons-material/PersonAddAlt1';
import { TStoreInfo } from '../../types';
import { storeFollow } from '../../apis/api/follow';
import { useParams } from 'react-router-dom';
import { QueryObserverResult } from 'react-query';

type StoreInfoProps = {
  store: TStoreInfo;
  refetch: () => Promise<QueryObserverResult<TStoreInfo, unknown>>;
};

const StoreInfo: React.FC<StoreInfoProps> = ({ store, refetch }) => {
  const { storeId } = useParams();

  const clickHandler = () => {
    storeFollow({
      userId: 1,
      storeId: Number(storeId),
    }).then(() => {
      refetch();
    });
  };

  return (
    <Grid container direction="column" mt={5}>
      <Typography variant="h4" fontWeight={'bold'}>
        {store.storeName}
      </Typography>
      <Typography fontStyle={{ color: '#888888' }}>{store.location}</Typography>
      <Typography margin={'50px 0'}>{store.description}</Typography>
      <Button
        variant="contained"
        color="button"
        style={{ height: 50 }}
        onClick={clickHandler}
      >
        <PersonAddAlt1Icon style={{ marginRight: '10px' }} />
        <Typography>팔로우 {store.followerNum}</Typography>
      </Button>
    </Grid>
  );
};

export default StoreInfo;
