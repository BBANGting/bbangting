import { Container } from '@mui/material';
import { useParams } from 'react-router-dom';
import StoreBanner from '../components/StoreDetail/StoreBanner';
import StoreInfo from '../components/StoreDetail/StoreInfo';
import StoreBreads from '../components/StoreDetail/StoreBreads';
import { useStoreDetail } from '../hooks/useStoreDetail';

const StoreDetail: React.FC = () => {
  const { storeId } = useParams<string>();
  const { isLoading, data, refetch } = useStoreDetail(storeId!);

  return (
    <Container disableGutters>
      {isLoading && <>Loading...</>}
      {data && (
        <>
          <StoreBanner
            storeImage={data.storeInfo.imgUrl2}
            storeLogo={data.storeInfo.imgUrl}
          />
          <StoreInfo store={data.storeInfo} refetch={refetch} />
          <StoreBreads bread={data.breadList} />
        </>
      )}
    </Container>
  );
};

export default StoreDetail;
