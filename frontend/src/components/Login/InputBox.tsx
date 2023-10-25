import { Typography, TextField } from '@mui/material';
import { useState } from 'react';

type InputBoxProps = {
  name: string;
  holder: string;
  type?: string;
};

export const InputBox = ({ name, holder, type }: InputBoxProps) => {
  const [isError, setIsError] = useState<boolean>(false);
  const [inputEmail, setInputEmail] = useState<string>('');

  const inputHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    e.preventDefault();
    setInputEmail(e.target.value);
    setIsError(!confirmEmail);
  };

  const confirmEmail: boolean = /^[\w+_]\w+@\w+\.\w+/.test(inputEmail);

  return (
    <>
      <Typography variant="subtitle1" mb={0.5}>
        {name}
      </Typography>
      <TextField
        required
        onChange={inputHandler}
        onKeyDown={() => setIsError(true)}
        error={isError}
        type={type}
        fullWidth
        placeholder={holder}
        sx={{ mb: 3.5 }}
        helperText="d"
      />
    </>
  );
};
