import { useParams } from 'react-router-dom';
import Purchase from '../components/BreadDetail/Purchase';
import { Container } from '@mui/material';
import ExtraInfo from '../components/BreadDetail/ExtraInfo';
import { useEffect, useState } from 'react';
import { getBreadInfo } from '../apis/api/breadInfo';
import { TBread } from '../types';

const BreadDetail: React.FC = () => {
  const { breadId } = useParams<string>();
  const [bread, setBread] = useState<TBread>({} as TBread);

  useEffect(() => {
    getBreadInfo(Number(breadId)).then(res => {
      setBread(res.breadDetail);
    });
  }, []);

  return (
    <Container disableGutters>
      {bread && (
        <>
          <Purchase bread={bread} />
          <ExtraInfo />
        </>
      )}
    </Container>
  );
};

export default BreadDetail;
