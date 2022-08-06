import * as React from 'react';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import { formatDate } from "../utils/DateUtils";
import { useEspressoSettingsService } from "../services/EspressoSettingsService";
import dayjs from "dayjs";
import {useEffect} from "react";
import {EspressoSetting} from "../models/EspressoSetting";
import {EspressoSettingDetails} from "../components/EspressoSettingDetails";

export const EspressoSettingsTable = () => {

  const { isFetching, getEspressoSet, data } = useEspressoSettingsService();

  useEffect(() => {
    getEspressoSet();
  }, []);

  const onRowClicked = (espressoSetting: EspressoSetting) => {
    console.log('details clicked: ' + espressoSetting.id);
    return <EspressoSettingDetails espressoSetting={espressoSetting} />
  }

  return (
    <TableContainer component={Paper}>
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
              <TableCell>{row.coffee.bakery}</TableCell>
              <TableCell>{formatDate(dayjs(row.coffee.roastedOnDate))}</TableCell>
              <TableCell>5/5</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
}