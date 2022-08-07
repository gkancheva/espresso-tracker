import * as React from "react";
import AddIcon from "@mui/icons-material/Add";
import Button from "@mui/material/Button";

import { useAuth } from "../services/AuthService";

interface AddButtonProps {
  onClick: () => void;
  text: string;
}

export const AddButton = ({ onClick, text }: AddButtonProps) => {
  const { isAuthenticated } = useAuth();

  return (
    <>
      { isAuthenticated() &&
        <Button
          onClick={onClick}
          size='small'
          style={{ margin: 4 }}
          variant="outlined"
          endIcon={<AddIcon />}>
          {text}
        </Button>
      }
    </>
  );

}