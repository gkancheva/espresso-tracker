import * as React from 'react';
import { useNavigate } from "react-router";
import { useEffect, useState } from "react";
import dayjs from "dayjs";
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import Button from "@mui/material/Button";
import AddIcon from '@mui/icons-material/Add';
import { formatDate } from "../utils/DateUtils";
import { useEspressoSettingsService } from "../services/EspressoSettingsService";
import { EspressoSetting } from "../models/EspressoSetting";
import { EspressoSettingDetails } from "../components/EspressoSettingDetails";
import { GlobalNotificationProps } from "../components/Notification";

export const EspressoSettingsTable = ({ showNotification }: GlobalNotificationProps) => {
  const [data, setData] = useState<EspressoSetting[]>([] as EspressoSetting[]);

  const { getEspressoSet } = useEspressoSettingsService(
    (data) => setData(data),
    (err) => showNotification(err, 'error')
  );
  const navigate = useNavigate();

  useEffect(() => {
    getEspressoSet();
  }, []);

  const onRowClicked = (espressoSetting: EspressoSetting) => {
    return <EspressoSettingDetails espressoSetting={espressoSetting} />
  }

  return (
    <TableContainer component={Paper}>
      <Button
        onClick={() => navigate('/add-new-espresso-shot')}
        size='small'
        style={{ margin: 4 }}
        variant="outlined"
        endIcon={<AddIcon />}>
        Add new shot
      </Button>
      <Table sx={{minWidth: 650}} aria-label='simple table'>
        <TableHead>
          <TableRow>
            <TableCell>Coffee name</TableCell>
            <TableCell>Bakery</TableCell>
            <TableCell>Roasted on</TableCell>
            <TableCell>Rating</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {data.map((row: EspressoSetting) => (
            <TableRow key={row.id} onClick={() => onRowClicked(row)}>
              <TableCell>{row.coffee.name}</TableCell>
              <TableCell>{row.coffee.bakery.name}</TableCell>
              <TableCell>{formatDate(dayjs(row.coffee.roastedOnDate))}</TableCell>
              <TableCell>5/5</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
}