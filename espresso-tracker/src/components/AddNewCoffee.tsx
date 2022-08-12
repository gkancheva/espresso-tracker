import React, { useEffect, useState } from "react";
import Button from "@mui/material/Button";
import Container from "@mui/material/Container";
import Typography from "@mui/material/Typography";
import Paper from "@mui/material/Paper";
import { DesktopDatePicker, LocalizationProvider } from "@mui/x-date-pickers";
import { FormControl, Grid, InputLabel, MenuItem, Select, SelectChangeEvent, TextField } from "@mui/material";
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import dayjs, { Dayjs } from "dayjs";
import { useNavigate } from "react-router";
import { CustomTextField } from "../components/CustomTextField";
import { GlobalNotificationProps } from "../components/Notification";
import { Bakery } from "../models/Bakery";
import { useBakeryService } from "../services/BakeryService";
import { checkInputIsValid, INPUT_TXT_MAX_LENGTH } from "../utils/Util";
import { useCoffeeService } from "../services/CoffeeService";

const DATE_FORMAT = 'DD-MM-YYYY'
const MAX_NAME_LENGTH = 100;

export const AddNewCoffee = ({ showNotification }: GlobalNotificationProps) => {
  const navigate = useNavigate();

  const [btnDisabled, setBtnDisabled] = useState(true);

  const [name, setName] = useState<string>('');
  const [bakeryName, setBakeryName] = useState<string>('');
  const [roastedOn, setRoastedOn] = useState<Dayjs>(dayjs());
  const [origin, setOrigin] = useState<string>('');
  const [description, setDescription] = useState<string | undefined>();
  const [bakeries, setBakeries] = useState<Bakery[]>([] as Bakery[]);

  const { getBakeryList } = useBakeryService(
    (data) => setBakeries(data),
    (err) =>  showNotification(err, 'error')
  );

  const getBakeryId = (bakeryName: string): number => {
    const bakeryId = bakeries[bakeries.findIndex((b) => b.name === bakeryName)].id;
    console.log("bakeryId: " + bakeryId);
    return bakeryId;
  }

  const { createCoffee } = useCoffeeService(
    () => {
      showNotification(`Successfully created coffee: '${name}'`);
      navigate(`/bakeries/${getBakeryId(bakeryName)}`)
    },
    (err) => showNotification(err, 'error')
  );

  useEffect(() => {
    getBakeryList();
  }, []);

  const handleDateChange = (input: string | null) => {
    const date = dayjs(input, DATE_FORMAT);
    setRoastedOn(date);
  }

  const checkAllInputsAreValid = () => {
    if (roastedOn.isAfter(dayjs())) {
      showNotification('Roasted on date must be not in the future', 'warning');
      return false;
    }
    return checkInputIsValid(name, MAX_NAME_LENGTH) &&
      checkInputIsValid(bakeryName) &&
      checkInputIsValid(origin, MAX_NAME_LENGTH) &&
      (description ? (description.trim().length < INPUT_TXT_MAX_LENGTH) : true);
  }

  const handleCreateNewCoffee = () => {
    const bakeryId = getBakeryId(bakeryName);
    if (!checkAllInputsAreValid() || bakeryId <= 0) {
      setBtnDisabled(true);
      showNotification('Fields are invalid, check each field text helper', 'error');
    }
    const request = {
      name: name,
      bakeryId: bakeryId,
      roastedOnDate: roastedOn!,
      origin: origin,
      description: description
    };
    createCoffee(request);
  }

  const handleBakerySelected = (event: SelectChangeEvent) => {
    const bakeryName = event.target.value as string;
    setBakeryName(bakeryName);
  };

  useEffect(() => {
    if (checkAllInputsAreValid()) {
      setBtnDisabled(false);
    }
  }, [name, bakeryName, origin, roastedOn, description]);

  return (
    <Container fixed>
      <Paper style={{ margin: 8, padding: 8 }}>
        <Typography variant={'h5'} style={{ margin: 2 }}>Add new Coffee</Typography>
        <CustomTextField
          label='Name'
          helperText='Name cannot be empty, length must be between 3 and 100 characters'
          onChange={(input) => setName(input)}
          checkInput={(input) => checkInputIsValid(input, MAX_NAME_LENGTH)} />
        <CustomTextField
          label={'Origin'}
          onChange={setOrigin}
          helperText={'Provide origin of the coffee beans. Length must be between 3 and 100 characters'}
          checkInput={(input) => checkInputIsValid(input, MAX_NAME_LENGTH)} />
        <CustomTextField
          label={'Description'}
          onChange={setDescription}
          helperText={'Provide description'}
          checkInput={(input) => input.trim().length < INPUT_TXT_MAX_LENGTH} />
        <Grid container spacing={2}>
          <Grid item xs={9}>
            <FormControl fullWidth style={{ marginTop: 10 }}>
              <InputLabel id="bakery-label-id">Bakery</InputLabel>
              <Select
                fullWidth
                labelId="bakery-label-id"
                id="bakery"
                value={bakeryName}
                label="Bakery"
                onChange={handleBakerySelected}>
                {
                  bakeries.map((bakery) => (
                    <MenuItem key={bakery.id} value={bakery.name}>{bakery.name}</MenuItem>
                  ))
                }
              </Select>
            </FormControl>
          </Grid>
          <Grid item xs={3}>
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
          </Grid>
        </Grid>
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
  );

}