import { Typography } from '@mui/material';

type InfoTitleProps = {
  children: React.ReactNode;
  sx?: Record<string, number>; // sx 속성에 대한 타입 정의
};

const InfoTitle: React.FC<InfoTitleProps> = ({ children, sx }) => {
  return (
    <Typography variant="h5" sx={{ fontWeight: 700, ...sx }}>
      {children}
    </Typography>
  );
};

export default InfoTitle;
