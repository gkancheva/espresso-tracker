import React from 'react';
import Alert, { AlertColor} from '@mui/material/Alert';

import { useState } from "react";
import { Snackbar } from "@mui/material";

interface NotificationProps {
  type?: AlertColor;
  message: string;
  isVisible: boolean;
}

export const Notification = ({ type, message, isVisible }: NotificationProps): JSX.Element => {
  const [visible, setVisible] = useState(isVisible);

  return (
    <Snackbar
      sx={{ minWidth: 600 }}
      open={visible}
      autoHideDuration={6000}
      anchorOrigin={{ vertical: 'top', horizontal: 'center' }}>
      <Alert severity={type} onClose={() => setVisible(false)}>{message}</Alert>
    </Snackbar>
  );
}