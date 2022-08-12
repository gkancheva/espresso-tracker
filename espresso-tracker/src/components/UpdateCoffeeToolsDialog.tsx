import * as React from "react";
import {AlertColor} from "@mui/material/Alert";
import { Dialog, DialogContent, DialogTitle } from "@mui/material";
import { EditableTextField } from "../components/EditableTextField";
import { CoffeeTool, CoffeeTools, CoffeeToolType, useCoffeeToolsService } from "../services/CoffeeToolsService";
import { checkInputIsValid } from "../utils/Util";
import { GlobalNotificationProps } from "../components/Notification";

interface Props extends GlobalNotificationProps {
  onCloseDialog: () => void;
  dialogIsOpen: boolean;
  onFinish: (name: CoffeeTools) => void;
  type: CoffeeToolType;
  tool?: CoffeeTool;
  showNotification: (message: string, type?: AlertColor) => void;
}

export const UpdateCoffeeToolsDialog = ({ onCloseDialog, dialogIsOpen, onFinish, type, tool, showNotification }: Props) => {
  const title = type === CoffeeToolType.COFFEE_MACHINE ? 'Coffee machine' : 'Coffee grinder';
  const helperText = type === CoffeeToolType.COFFEE_MACHINE ? 'Provide coffee machine name' : 'Provide grinder name';

  const { sendCreateUpdateRequest } = useCoffeeToolsService(
    (data) => onFinish(data),
    (err) => showNotification(err, 'error')
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