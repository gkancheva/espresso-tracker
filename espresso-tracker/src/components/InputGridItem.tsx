import { Grid, InputAdornment, TextField } from "@mui/material";
import React, {useState} from "react";

interface Props {
  value: string | number;
  isValid: (input: number) => boolean;
  id: string;
  label: string;
  suffix?: string;
  handleOnChange: (inout: number) => void;
  helperText: string;
}


export const InputGridItem = ({ value, isValid, id, label, suffix, handleOnChange, helperText }: Props) => {
  const [hasError, setHasError] = useState(false);

  const validate = (input: string) => {
    console.log(input);
    const asNum = Number.parseInt(input);
    if (!isValid(asNum)) {
      setHasError(true);
      return;
    }
    setHasError(false);
    handleOnChange(asNum);
  }

  return (
    <Grid item xs={3}>
      <TextField
        error={hasError}
        id={id}
        label={label}
        type={'number'}
        defaultValue={value}
        inputProps={{ style: { textAlign: 'right' } }}
        InputLabelProps={{ shrink: true }}
        fullWidth
        variant={'outlined'}
        InputProps={{
          endAdornment: <InputAdornment position='end'>{suffix}</InputAdornment>,
        }}
        onChange={(event) => validate(event.target.value)}
        helperText={hasError && helperText}
      />
    </Grid>
  );
}