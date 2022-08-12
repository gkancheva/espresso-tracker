import React, {useEffect} from 'react';
import Alert, { AlertColor} from '@mui/material/Alert';

import { useState } from "react";

export interface GlobalNotificationProps {
  showNotification: (message: string, type?: AlertColor) => void;
}

interface NotificationProps {
  type?: AlertColor;
  message: string;
  isVisible: boolean;
  notifyIsVisible: (isVisible: boolean) => void;
}

const TIMEOUT_SECONDS = 30000;

export const Notification = ({ type, message, isVisible, notifyIsVisible }: NotificationProps): JSX.Element => {
  const [visible, setIsVisible] = useState(isVisible);

  useEffect(() => {
    setIsVisible(isVisible);
  }, [isVisible]);

  useEffect(() => {
    const timeId = setTimeout(() => {
      setIsVisible(false)
      notifyIsVisible(false);
    }, TIMEOUT_SECONDS);

    return () => clearTimeout(timeId);
  }, []);

  return (
    <>
      { visible &&
        <Alert severity={type ? type : 'error'} onClick={() => {
          setIsVisible(false);
          notifyIsVisible(false);
        }}>{message}</Alert>
      }
    </>
  );
}