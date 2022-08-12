import TableHead from "@mui/material/TableHead";
import Table from "@mui/material/Table";
import Typography from "@mui/material/Typography";
import TableRow from "@mui/material/TableRow";
import TableCell from "@mui/material/TableCell";
import TableBody from "@mui/material/TableBody";
import dayjs from "dayjs";
import * as React from "react";
import { Coffee } from "../models/Coffee";
import { formatDate } from "../utils/DateUtils";

interface RowProps {
  coffee: Coffee;
}

interface TableProps {
  coffees: Coffee[];
}

const MAX_WIDTH = 70;

export const CoffeeHeader = () => {
  return (
    <TableHead>
      <TableRow>
        <TableCell align='center' style={{ width: MAX_WIDTH }}>Coffee name</TableCell>
        <TableCell align='center'  style={{ width: MAX_WIDTH }}>Roasted on</TableCell>
        <TableCell align='center' style={{ width: MAX_WIDTH }}>Origin</TableCell>
        <TableCell>Description</TableCell>
      </TableRow>
    </TableHead>
  );
}

export const CoffeeRow = ({ coffee }: RowProps) => {
  return (
    <TableRow key={coffee.name}>
      <TableCell align='center' style={{ width: MAX_WIDTH }}>{coffee.name}</TableCell>
      <TableCell align='center' style={{ width: MAX_WIDTH }}>{formatDate(dayjs(coffee.roastedOnDate))}</TableCell>
      <TableCell align='center' style={{ width: MAX_WIDTH }}>{coffee.origin}</TableCell>
      <TableCell>{coffee.description}</TableCell>
    </TableRow>
  );
}

export const CoffeeTable = ({ coffees }: TableProps) => {
  return (
    <>
      <Typography variant="subtitle1" color="text.secondary" mx={2}>
        List of available coffees
      </Typography>
      <Table>
        <CoffeeHeader />
        <TableBody>
          {
            coffees.map((coffee) => <CoffeeRow key={coffee.id} coffee={coffee} />)
          }
        </TableBody>
      </Table>
    </>
  );
}