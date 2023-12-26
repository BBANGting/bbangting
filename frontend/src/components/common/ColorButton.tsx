import { Button, ButtonProps } from '@mui/material';

type ColorButtonProps = {
  children: React.ReactNode;
  sx?: Record<string, string | number>; // sx 속성에 대한 타입 정의
  fullWidth?: boolean;
  onClick?: ButtonProps['onClick']; // onClick 프로퍼티 추가
};

const ColorButton: React.FC<ColorButtonProps> = ({
  sx,
  children,
  fullWidth,
  onClick,
}) => {
  return (
    <Button
      fullWidth={fullWidth}
      variant="contained"
      color="button"
      sx={{ ...sx }}
      onClick={onClick}
    >
      {children}
    </Button>
  );
};

export default ColorButton;
