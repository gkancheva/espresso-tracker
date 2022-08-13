import Paper from "@mui/material/Paper";
import React, {useEffect, useState} from "react";
import { useNavigate } from "react-router";
import Typography from "@mui/material/Typography";
import { Grid, InputAdornment, MenuItem, TextField } from "@mui/material";
import Button from "@mui/material/Button";
import { Coffee } from "../models/Coffee";
import { useCoffeeService } from "../services/CoffeeService";
import { InputGridItem } from "../components/InputGridItem";
import { useEspressoSettingsService } from "../services/EspressoSettingsService";
import { checkNumberInput } from "../utils/Util";
import { GlobalNotificationProps } from "../components/Notification";

const MAX_GRINDING_FINESSE_LENGTH = 150;

export const AddNewEspressoShot = ({ showNotification }: GlobalNotificationProps) => {
  const navigate = useNavigate();

  const [btnDisabled, setBtnDisabled] = useState(true);
  const [coffees, setCoffees] = useState<Coffee[]>([] as Coffee[]);
  const [coffeeId, setCoffeeId] = useState<number>(-1);
  const [dose, setDose] = useState<number>(18);
  const [brewingTemperature, setBrewingTemperature] = useState<number>(92);
  const [brewingPressure, setBrewingPressure] = useState<number>(9);
  const [volume, setVolume] = useState<number>(25);
  const [grindingFinnesse, setGrindingFinesse] = useState<string>('');
  const [extractDurationSec, setExtractDurationSec] = useState<number>(20);
  const [grindingInError, setGrindingInError] = useState(false);

  const { getCoffees } = useCoffeeService(
    (data) => setCoffees(data),
    (err) => showNotification(err, 'error'));

  const { createEspressoSetting } = useEspressoSettingsService(
    () => {
      showNotification(`Successfully created espresso shot setting`, 'success');
      navigate('/espresso-list')
    },
    (err) => showNotification(err, 'error')
  );

  useEffect(() => {
    getCoffees();
  }, []);

  const handleSelectChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const id = Number.parseInt(event.target.value);
    setCoffeeId(id);
  };

  useEffect(() => {
    if (coffeeId > 0 && dose && brewingPressure && brewingTemperature && volume && grindingFinnesse && extractDurationSec) {
      setBtnDisabled(false);
    }

  }, [coffeeId, dose, brewingTemperature,
    brewingPressure, volume, grindingFinnesse, extractDurationSec]);

  const handleOnClick = () => {
    const request = {
      coffeeId: coffeeId,
      dose: dose,
      grindingFineness: grindingFinnesse,
      brewingTemperature: brewingTemperature,
      brewingPressure: brewingPressure,
      volume: volume,
      extractDurationSec: extractDurationSec
    }
    createEspressoSetting(request);
  }

  const checkGrindingFinesse = (value?: string) => {
    if (!value || value.trim().length > MAX_GRINDING_FINESSE_LENGTH) {
      setGrindingInError(true);
      return;
    }
    setGrindingInError(false);
    setGrindingFinesse(value);
  }

  return (
    <Paper style={{padding: 20, margin: '20px auto'}}>
      <Typography variant={'h5'} style={{ margin: 2 }}>Add new Espresso shot</Typography>
      <Grid
        container
        spacing={2}
        marginY={2}
        alignItems="center"
        justifyContent="center">
        <Grid item xs={6}>
          <TextField
            select
            fullWidth
            id='coffee-id'
            label='Coffee'
            type='text'
            value={coffeeId}
            onChange={handleSelectChange}
            inputProps={{ style: { textAlign: 'center' } }}
            InputLabelProps={{
              shrink: true,
            }}
            variant={'outlined'}
            InputProps={{
              endAdornment: <InputAdornment position='end'/>,
            }}>
            {coffees.map((coffee) => (
              <MenuItem key={coffee.id} value={coffee.id}>
                {coffee.name}
              </MenuItem>
            ))}
          </TextField>
        </Grid>
        <Grid item xs={3}>
          <TextField
            error={grindingInError}
            id='grinding-finesse'
            label='Coffee grind size'
            type={'string'}
            defaultValue={grindingFinnesse}
            inputProps={{ style: { textAlign: 'right' } }}
            InputLabelProps={{ shrink: true }}
            fullWidth
            variant={'outlined'}
            helperText={grindingInError && 'Coffee grind setting should not exceed 100 characters'}
            onChange={(event) => checkGrindingFinesse(event.target.value)}
          />
        </Grid>

        <InputGridItem
          id='dose'
          label='Dose'
          value={dose}
          suffix='gr'
          helperText='Dose must be between 7 and 25'
          isValid={(input) => checkNumberInput(input, 7, 25)}
          handleOnChange={(input) => setDose(input)} />
        <InputGridItem
          id='brewing-temperature'
          label='Brewing temperature'
          value={brewingTemperature}
          suffix='°C'
          helperText='Brewing temperature is a value between 80 and 100'
          isValid={(input) => checkNumberInput(input, 80, 100)}
          handleOnChange={(input) => setBrewingTemperature(input)} />
        <InputGridItem
          id='brewing-pressure'
          label='Brewing pressure'
          value={brewingPressure}
          suffix='bar'
          helperText='Brewing pressure is a value between 2 and 16'
          isValid={(input) => checkNumberInput(input, 2, 16)}
          handleOnChange={(input) => setBrewingPressure(input)} />
        <InputGridItem
          id='volume'
          label='Volume'
          value={volume}
          suffix='ml'
          helperText='Volume is a value between 5 and 150'
          isValid={(input) => checkNumberInput(input, 5, 150)}
          handleOnChange={(input) => setVolume(input)} />
        <InputGridItem
          id='extract-duration-sec'
          label='Extract duration in seconds'
          value={extractDurationSec}
          suffix='sec'
          isValid={(input) => checkNumberInput(input, 10, 60)}
          helperText='Extract duration is a value between 10 and 60'
          handleOnChange={(input) => setExtractDurationSec(input)} />
        <Grid item xs={6} id='btn-id'>
          <Button
            style={{ marginTop: 8 }}
            disabled={btnDisabled}
            variant={'contained'}
            type="submit"
            fullWidth
            onClick={handleOnClick}>
            Add espresso setting
          </Button>
        </Grid>
      </Grid>
    </Paper>
  );

}