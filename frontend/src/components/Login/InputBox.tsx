import { Typography, TextField } from '@mui/material';

type InputBoxProps = {
  name: string;
  holder: string;
  type?: string;
};

export const InputBox = ({ name, holder, type }: InputBoxProps) => {
  return (
    <>
      <Typography variant="subtitle1" mb={0.5}>
        {name}
      </Typography>
      <TextField type={type} fullWidth placeholder={holder} sx={{ mb: 3.5 }} />
    </>
  );
};
