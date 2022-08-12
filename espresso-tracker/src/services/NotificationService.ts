import { useState } from "react";
import { AlertColor } from "@mui/material/Alert";

export interface UseNotificationProps {
  showNotification: (message: string, type?: AlertColor) => void;
}

export interface NotificationProps {
  type?: AlertColor;
  message: string;
  isVisible: boolean;
  updateVisibility: (isVisible: boolean) => void;
  showNotification: (message: string, type?: AlertColor) => void;
}

const DEFAULT_NOTIFICATION_TYPE = 'success';

export const useNotification = (): NotificationProps => {
  const [message, setMessage] = useState<string>('');
  const [type, setType] = useState<AlertColor>(DEFAULT_NOTIFICATION_TYPE);
  const [isVisible, setIsVisible] = useState(false);

  const showNotification = (message: string, type?: AlertColor) => {
    console.log("Show notification: type[" + type + "], message: [" + message + "]");
    setMessage(message);
    setType(type ? type : DEFAULT_NOTIFICATION_TYPE);
    setIsVisible(true);
  }

  const updateVisibility = (visible: boolean) => setIsVisible(visible);

  return { type, message, isVisible, updateVisibility, showNotification }
}