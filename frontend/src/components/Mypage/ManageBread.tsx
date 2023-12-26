import { Grid } from '@mui/material';
import InfoTitle from './InfoTitle';
import ManageBreadCard from './ManageBreadCard';
import { useRecoilValue } from 'recoil';
import { storeBreadState } from '../../store/state';

const ManageBread: React.FC = () => {
  const storeBread = useRecoilValue(storeBreadState);

  return (
    <>
      <InfoTitle sx={{ mt: 15 }}>빵 관리</InfoTitle>
      <Grid container sx={{ overflowX: 'auto' }}>
        {storeBread.map(item => (
          <ManageBreadCard
            key={item.breadId}
            img={item.imgUrl}
            name={item.breadName}
          />
        ))}
        <ManageBreadCard
          img={'/imgs/newBread.png'}
          name="새 빵팅 등록"
          isBtn={true}
        />
      </Grid>
    </>
  );
};

export default ManageBread;
