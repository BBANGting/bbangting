import { useParams } from 'react-router-dom';
import store from '../json/store.json';

const StoreDetail: React.FC = () => {
  const { storeId } = useParams<string>();
  console.log(store.filter(item => item.storeId === Number(storeId)));

  return <>store detail</>;
};

export default StoreDetail;
