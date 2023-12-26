import { Grid } from '@mui/material';
import StoreCard from './StoreCard';
import { useStoreList } from '../../hooks/useStoreList';
import { TStoreInfo } from '../../types';

const StoreContainer = () => {
  const { isLoading, data: storeList } = useStoreList();
  return (
    <Grid container alignItems="center" height={'100%'} mt={5}>
      {isLoading && <>Loading...</>}
      {storeList &&
        storeList.storeInfoList.map((store: TStoreInfo) => (
          <StoreCard
            key={store.storeId}
            storeId={store.storeId}
            img={store.imgUrl}
            name={store.storeName}
          />
        ))}
    </Grid>
  );
};

export default StoreContainer;
