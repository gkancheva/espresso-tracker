import React, { useEffect, useState } from "react";
import Button from "@mui/material/Button";
import Container from "@mui/material/Container";
import Typography from "@mui/material/Typography";
import Paper from "@mui/material/Paper";
import { DesktopDatePicker, LocalizationProvider} from "@mui/x-date-pickers";
import { InputLabel, MenuItem, Select, SelectChangeEvent, TextField } from "@mui/material";
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';

import { AlertColor } from "@mui/material/Alert";
import dayjs, { Dayjs } from "dayjs";
import { CustomTextField } from "../components/CustomTextField";
import { Notification } from "../components/Notification";
import { Bakery } from "../models/Bakery";
import { useBakeryService } from "../services/BakeryService";
import { checkInput, INPUT_TXT_MAX_LENGTH } from "../utils/StringUtil";

const DATE_FORMAT = 'DD-MM-YYYY'

export const AddNewCoffee = () => {
  const [message, setMessage] = useState("");
  const [notificationVisible, setNotificationVisible] = useState(false);
  const [notificationType, setNotificationType] = useState<AlertColor>();
  const [btnDisabled, setBtnDisabled] = useState(true);

  const [name, setName] = useState<string>('');
  const [bakeryName, setBakeryName] = useState<string>('');
  const [roastedOn, setRoastedOn] = useState<Dayjs | null>();
  const [origin, setOrigin] = useState<string>('');
  const [description, setDescription] = useState<string | undefined>();
  const [bakeries, setBakeries] = useState<Bakery[]>([] as Bakery[]);

  const { getBakeryList } = useBakeryService(
    (data) => setBakeries(data),
    (err) => {
      setMessage(err);
      setNotificationType('error');
      setNotificationVisible(true);
    }
  );

  useEffect(() => {
    getBakeryList();
  }, []);

  const handleDateChange = (input: string | null) => {
    const date = dayjs(input, DATE_FORMAT);
    setRoastedOn(date);
  }

  const handleCreateNewCoffee = () => {
    const bakeryId = bakeries[bakeries.findIndex((b) => b.name === bakeryName)].id;
    const request = {
      name: name,
      bakeryId: bakeryId,
      roastedOnDate: roastedOn,
      origin: origin,
      description: description
    };
    console.log("Request: " + JSON.stringify(request));
  }

  const handleBakerySelected = (event: SelectChangeEvent) => {
    const bakeryName = event.target.value as string;
    setBakeryName(bakeryName);
  };

  useEffect(() => {
    if (checkInput(name) && checkInput(bakeryName) && checkInput(origin) && roastedOn) {
      setBtnDisabled(false);
    }
  }, [name, bakeryName, origin, roastedOn, description]);

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
        <Typography style={{ margin: 2 }}>Add new Coffee</Typography>
        <Paper style={{ margin: 8, padding: 8 }}>
          <CustomTextField
            label='Name'
            helperText='Name cannot be empty'
            onChange={(input) => setName(input)}
            checkInput={(input) => checkInput(input)} />

          <InputLabel id="bakery-label-id">Bakery</InputLabel>
          <Select
            labelId="bakery-label-id"
            id="bakery"
            value={bakeryName}
            label="Bakery"
            fullWidth
            onChange={handleBakerySelected}>
            {
              bakeries.map((bakery) => (
                <MenuItem key={bakery.id} value={bakery.name}>{bakery.name}</MenuItem>
              ))
            }
          </Select>

          <LocalizationProvider dateAdapter={AdapterDayjs}>
            <div style={{ marginTop: 8 }}>
              <DesktopDatePicker
                disableFuture
                label="Roasted on date"
                inputFormat={DATE_FORMAT}
                value={roastedOn}
                onChange={handleDateChange}
                renderInput={(params) => <TextField {...params} />}
              />
            </div>
          </LocalizationProvider>

          <CustomTextField
            label={'Origin'}
            onChange={setOrigin}
            helperText={'Provide origin of the coffee beans'}
            checkInput={(input) => checkInput(input)} />
          <CustomTextField
            label={'Description'}
            onChange={setDescription}
            helperText={'Provide description'}
            checkInput={(input) => input.trim().length < INPUT_TXT_MAX_LENGTH} />
          <Button
            style={{ marginTop: 8 }}
            disabled={btnDisabled}
            variant={'contained'}
            type="submit"
            fullWidth
            onClick={handleCreateNewCoffee}>
            Create coffee
          </Button>
        </Paper>
      </Container>
    </>
  );

}