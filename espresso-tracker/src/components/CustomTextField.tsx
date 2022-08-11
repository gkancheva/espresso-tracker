import { TextField } from "@mui/material";
import React, {useState} from "react";

interface Props {
  label: string;
  onChange: (input: string) => void;
  checkInput: (input: string) => boolean;
  helperText?: string;
  required?: boolean;
  defaultValue?: string;
}

export const CustomTextField = ({ label, onChange, checkInput, helperText, required, defaultValue }: Props) => {
  const [hasError, setHasError] = useState(false);

  const handleOnChange = (input: string) => {
    if (!checkInput(input)) {
      setHasError(true);
      return;
    }
    setHasError(false);
    onChange(input);
  }
  
  return (
    <TextField
      error={hasError}
      defaultValue={defaultValue}
      style={{marginTop: 8}}
      label={label}
      type='text'
      fullWidth
      required={required}
      variant={'standard'}
      onChange={(event) => handleOnChange(event.target.value)}
      helperText={hasError && helperText} />
  );
}