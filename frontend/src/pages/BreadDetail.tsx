import { useParams } from 'react-router-dom';

const BreadDetail: React.FC = () => {
  const { breadId } = useParams<string>();

  return (
    <>
      <>{breadId}</>
    </>
  );
};

export default BreadDetail;
