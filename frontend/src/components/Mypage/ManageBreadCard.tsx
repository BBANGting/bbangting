import { Grid, IconButton, Typography } from '@mui/material';
import EditIcon from '@mui/icons-material/Edit';
import { Link } from 'react-router-dom';
import { useRecoilValue } from 'recoil';
import { storeIdState } from '../../store/store';

type ManageBreadCardProps = {
  img: string;
  name: string;
  isBtn?: boolean; // 새 빵팅 등록하는 card면 editIcon 안 보이게 설정
};

const ManageBreadCard: React.FC<ManageBreadCardProps> = ({
  img,
  name,
  isBtn,
}) => {
  const storeId = useRecoilValue(storeIdState);

  return (
    <Grid
      item
      xs={4}
      sx={{
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        height: 300,
        flexWrap: 'nowrap',
      }}
    >
      <Grid
        container
        width={230}
        height={260}
        boxShadow={'1px 1px 5px 0px rgba(0, 0, 0, 0.05)'}
        borderRadius={5}
        justifyContent="center"
        alignItems="center"
        flexDirection="column"
        position="relative"
      >
        {isBtn ? (
          <Link to={`/new/bread/${storeId}`}>
            <img src={img} alt="hi" width={160} height={160} />
          </Link>
        ) : (
          <img src={img} alt="hi" width={160} height={160} />
        )}
        <Grid display="flex" mt={3}>
          <Typography fontWeight={'bold'}>{name}</Typography>
          {!isBtn && (
            <IconButton sx={{ color: '#dddddd', padding: 0.5 }}>
              <EditIcon
                fontSize="small"
                sx={{ width: '16px', height: '16px' }}
              />
            </IconButton>
          )}
        </Grid>
      </Grid>
    </Grid>
  );
};

export default ManageBreadCard;
