import { useParams } from 'react-router-dom';
import Purchase from '../components/BreadDetail/Purchase';
import { Container } from '@mui/material';
import ExtraInfo from '../components/BreadDetail/ExtraInfo';
import { useEffect, useState } from 'react';
import { getBreadInfo } from '../apis/api/breadInfo';

const BreadDetail: React.FC = () => {
  const { breadId } = useParams<string>();
  const [bread, setBread] = useState({});
  useEffect(() => {
    getBreadInfo(Number(breadId)).then(res => {
      console.log(res);
      setBread(res.breadDetail);
    });
  }, []);

  return (
    <Container disableGutters>
      <Purchase bread={bread} />
      <ExtraInfo />
    </Container>
  );
};

export default BreadDetail;
