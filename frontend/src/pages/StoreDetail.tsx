import { Container } from '@mui/material';
import { useParams } from 'react-router-dom';
import StoreBanner from '../components/StoreDetail/StoreBanner';
import StoreInfo from '../components/StoreDetail/StoreInfo';
import StoreBreads from '../components/StoreDetail/StoreBreads';
import { getStoreInfo } from '../apis/api/storeInfo';
import { useEffect, useState } from 'react';
import { TStoreInfo } from '../types';

const StoreDetail: React.FC = () => {
  const [store, setStore] = useState<TStoreInfo>({} as TStoreInfo);
  const [breadList, setBreadList] = useState([]);

  const { storeId } = useParams<string>();
  useEffect(() => {
    getStoreInfo(Number(storeId)).then(res => {
      console.log(res);
      setStore(res.storeInfo);
      setBreadList(res.breadList);
    });
  }, []);

  return (
    <Container disableGutters>
      <StoreBanner storeImage={store.imgUrl2} storeLogo={store.imgUrl} />
      <StoreInfo store={store} />
      <StoreBreads bread={breadList} />
    </Container>
  );
};

export default StoreDetail;
