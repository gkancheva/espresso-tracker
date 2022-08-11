import { useState } from "react";
import CheckIcon from '@mui/icons-material/Check';
import Button from "@mui/material/Button";
import * as React from "react";
import { Box } from "@mui/material";
import { checkInputIsValid } from "../utils/StringUtil";
import { CustomTextField } from "../components/CustomTextField";

interface Props {
  value?: string;
  label: string
  helperText?: string;
  onEditFinished: (text?: string) => void;
}

const MAX_LENGTH_NAME = 100;

export const EditableTextField = ({ label, value, helperText, onEditFinished }: Props ) => {
  const [editing, setEditing] = useState(false);
  const [currentValue, setCurrentValue] = useState(value);

  return (
    <>
      {
        editing ?
          <div>
            <Box sx={{ display: 'flex', alignItems: 'flex-end' }}>
              <CustomTextField
                defaultValue={value}
                label={label}
                helperText={helperText}
                onChange={(input) => setCurrentValue(input)}
                checkInput={(input) => checkInputIsValid(input, MAX_LENGTH_NAME)} />
              <CheckIcon onClick={() => onEditFinished(currentValue)} sx={{ color: 'action.active', mr: 1, my: 0.5 }} />
            </Box>
          </div>
           :
          <Button onClick={() => setEditing(true)} variant='text'>
            {currentValue ? currentValue : 'N/A: Click to add'}
          </Button>
      }
    </>
  );

}