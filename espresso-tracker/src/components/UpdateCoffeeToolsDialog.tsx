import * as React from "react";
import { Dialog, DialogContent, DialogTitle } from "@mui/material";
import { EditableTextField } from "../components/EditableTextField";
import { CoffeeTool, CoffeeTools, CoffeeToolType, useCoffeeToolsService } from "../services/CoffeeToolsService";
import { checkInputIsValid } from "../utils/StringUtil";
import { Notification } from "../components/Notification";
import { useState } from "react";

interface Props {
  onCloseDialog: () => void;
  dialogIsOpen: boolean;
  onFinish: (name: CoffeeTools) => void;
  type: CoffeeToolType;
  tool?: CoffeeTool;
}

export const UpdateCoffeeToolsDialog = ({ onCloseDialog, dialogIsOpen, onFinish, type, tool }: Props) => {
  const title = type === CoffeeToolType.COFFEE_MACHINE ? 'Coffee machine' : 'Coffee grinder';
  const helperText = type === CoffeeToolType.COFFEE_MACHINE ? 'Provide coffee machine name' : 'Provide grinder name';

  const [notification, setNotification] = useState<string>('');
  const [notificationIsVisible, setNotificationIsVisible] = useState(false);

  const { sendCreateUpdateRequest } = useCoffeeToolsService(
    (data) => onFinish(data),
    (err) => {
      setNotification(err);
      setNotificationIsVisible(true);
    }
  );

  const sendSaveRequest = (coffeeToolType: CoffeeToolType, name?: string) => {
    if (name && checkInputIsValid(name)) {
      const request = {
        coffeeToolType: coffeeToolType,
        name: name
      }
      sendCreateUpdateRequest(request);
    }
  }

  return (
    <Dialog
      style={{ minWidth: 500 }}
      onClose={onCloseDialog}
      open={dialogIsOpen} >
      {
        notificationIsVisible &&
        <Notification
          type={'error'}
          message={notification}
          isVisible={notificationIsVisible}
          notifyIsVisible={setNotificationIsVisible} />
      }
      <DialogTitle>{title}</DialogTitle>
      <DialogContent style={{ margin: 2 }} >
        <EditableTextField
          value={tool?.name}
          label={helperText}
          onEditFinished={(name) => sendSaveRequest(type, name)} />
      </DialogContent>
    </Dialog>
  );
}