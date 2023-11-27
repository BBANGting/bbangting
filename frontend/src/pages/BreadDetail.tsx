import { useParams } from 'react-router-dom';
import Purchase from '../components/BreadDetail/Purchase';
import temp_bread from '../components/json/bread.json';
import { Container } from '@mui/material';

const BreadDetail: React.FC = () => {
  const { breadId } = useParams<string>();

  const bread = temp_bread.filter(
    bread => bread.breadId === Number(breadId),
  )[0];

  return (
    <Container disableGutters>
      <Purchase bread={bread} />
    </Container>
  );
};

export default BreadDetail;
