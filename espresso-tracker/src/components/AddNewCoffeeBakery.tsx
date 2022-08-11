import React, { useEffect, useState } from "react";
import Paper from "@mui/material/Paper";
import Button from "@mui/material/Button";

import { Notification } from "../components/Notification";
import { CustomTextField } from "../components/CustomTextField";
import { useBakeryService } from "../services/BakeryService";
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import { AlertColor } from "@mui/material/Alert";
import { useNavigate } from "react-router";
import { checkInputIsValid } from "../utils/StringUtil";

const MAX_LENGTH = 30;

export const AddNewCoffeeBakery = () => {
  const navigate = useNavigate();
  const [message, setMessage] = useState("");
  const [notificationVisible, setNotificationVisible] = useState(false);
  const [notificationType, setNotificationType] = useState<AlertColor>();
  const [btnDisabled, setBtnDisabled] = useState(true);

  const [name, setName] = useState<string>('');
  const [address, setAddress] = useState<string>('');
  const [phone, setPhone] = useState<string>('');
  const [webSite, setWebSite] = useState<string>('');
  const [imgSource, setImgSource] = useState<string | undefined>();

  const updateNotification = (message: string, type: AlertColor) => {
    setMessage(message);
    setNotificationType(type);
    setNotificationVisible(true);
  }

  const { sendCreateBakery } = useBakeryService(
    () =>  {
      updateNotification(`Bakery '${name}' successfully created`, 'success');
      navigate('/bakeries');
    },
    (err) => updateNotification(err, 'error')
  );

  const verifyAllFields = () => {
    return checkInputIsValid(name, MAX_LENGTH) &&
      checkInputIsValid(address) &&
      checkInputIsValid(phone, MAX_LENGTH) &&
      checkInputIsValid(webSite);
  }

  useEffect(() => {
    if (verifyAllFields()) {
      setBtnDisabled(false);
    }
  }, [name, address, phone, webSite, imgSource]);

  const handleCreateNewBakery = () => {
    const request = {
      name: name,
      address: address,
      phone: phone,
      webSite: webSite,
      imgSource: imgSource
    }
    sendCreateBakery(request);
  }

  return (
    <>
      {
        notificationVisible &&
        <Notification
          type={notificationType}
          message={message}
          isVisible={notificationVisible}
          notifyIsVisible={setNotificationVisible} />
      }
      <Container fixed>
        <Typography style={{ margin: 2 }}>Add new Bakery</Typography>
        <Paper style={{ margin: 8, padding: 8 }}>
          <CustomTextField
            label='Name'
            helperText='Name cannot be empty'
            onChange={(input) => setName(input)}
            checkInput={(input) => checkInputIsValid(input, MAX_LENGTH)} />
          <CustomTextField
            label={'Address'}
            onChange={setAddress}
            helperText='Address must be at least three character long'
            checkInput={(input) => checkInputIsValid(input)} />
          <CustomTextField
            label={'Phone number'}
            onChange={setPhone}
            helperText={'Provide phone number for contact'}
            checkInput={(input) => checkInputIsValid(input, MAX_LENGTH)} />
          <CustomTextField
            label={'Web site'}
            onChange={setWebSite}
            helperText={'Provide url to web site'}
            checkInput={(input) => checkInputIsValid(input)} />
          <CustomTextField
            label={'Image source url'}
            onChange={setImgSource}
            helperText={'Provide image source'}
            checkInput={() => true} />
          <Button
            style={{ marginTop: 8 }}
            disabled={btnDisabled}
            variant={'contained'}
            type="submit"
            fullWidth
            onClick={handleCreateNewBakery}>
            Create bakery
          </Button>
        </Paper>
      </Container>
    </>
  );
}