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
  const [breadDetailImg, setBreadDetailImg] = useState<string>('');

  useEffect(() => {
    getBreadInfo(Number(breadId)).then(res => {
      console.log(res);
      setBread(res.breadDetail);
      setBreadDetailImg(res.breadDetailPage[2]);
    });
  }, []);

  return (
    <Container disableGutters>
      {bread && (
        <>
          <Purchase bread={bread} />
          <ExtraInfo breadDetailImg={breadDetailImg} />
        </>
      )}
    </Container>
  );
};

export default BreadDetail;
