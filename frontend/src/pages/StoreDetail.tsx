import { Container } from '@mui/material';
import temp_store from '../components/json/store.json';
import temp_bread from '../components/json/bread.json';
import { useParams } from 'react-router-dom';
import StoreBanner from '../components/StoreDetail/StoreBanner';
import StoreInfo from '../components/StoreDetail/StoreInfo';
import StoreBreads from '../components/StoreDetail/StoreBreads';

const StoreDetail: React.FC = () => {
  const { storeId } = useParams<string>();

  const store = temp_store.filter(
    store => store.storeId === Number(storeId),
  )[0];

  const bread = temp_bread.filter(bread => bread.storeId === Number(storeId));
  return (
    <Container disableGutters>
      <StoreBanner storeImage={store.storeImage} storeLogo={store.storeLogo} />
      <StoreInfo store={store} />
      <StoreBreads bread={bread} />
    </Container>
  );
};

export default StoreDetail;
