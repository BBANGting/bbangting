import { Container } from '@mui/material';
import temp_store from '../components/json/store.json';
import { useParams } from 'react-router-dom';
import StoreBanner from '../components/StoreDetail/StoreBanner';
import StoreInfo from '../components/StoreDetail/StoreInfo';

const StoreDetail: React.FC = () => {
  const { storeId } = useParams<string>();
  const store = temp_store.filter(
    store => store.storeId === Number(storeId),
  )[0];
  return (
    <Container disableGutters>
      <StoreBanner storeImage={store.storeImage} storeLogo={store.storeLogo} />
      <StoreInfo store={store} />
    </Container>
  );
};

export default StoreDetail;
