import { GridColDef } from "@mui/x-data-grid";
import { formatNumber } from "../utils/Util";

export const espressoSettingsColumns: GridColDef[] = [
  {
    field: 'coffee',
    align: 'left',
    headerName: 'Coffee name',
    minWidth: 200,
    flex: 1,
    sortable: true,
    valueGetter: (param) => param.row.coffee.name
  },
  {
    field: 'dose',
    align: 'right',
    headerName: 'Dose',
    maxWidth: 150,
    type: 'number',
    flex: 1,
    valueGetter: param => `${formatNumber(param.row.dose)} gr`
  },
  {
    field: 'grindingFineness',
    align: 'right',
    headerName: 'Grinding finesse',
    maxWidth: 150,
    flex: 1,
    valueGetter: param => param.row.grindingFineness
  },
  {
    field: 'brewingTemperature',
    align: 'right',
    headerName: 'Brewing temperature',
    maxWidth: 150,
    type: 'number',
    flex: 1,
    valueGetter: param => `${formatNumber(param.row.brewingTemperature)} °C`
  },
  {
    field: 'brewingPressure',
    align: 'right',
    headerName: 'Brewing pressure',
    maxWidth: 150,
    type: 'number',
    flex: 1,
    valueGetter: param => `${formatNumber(param.row.brewingPressure)} bar`
  },
  {
    field: 'volume',
    align: 'right',
    headerName: 'Volume',
    maxWidth: 150,
    type: 'number',
    flex: 1,
    valueGetter: param => `${formatNumber(param.row.volume)} ml`
  },
  {
    align: 'right',
    field: 'extractDurationSec',
    headerName: 'Extract duration (sec)',
    maxWidth: 150,
    type: 'number',
    flex: 1,
    valueGetter: param => `${param.row.extractDurationSec} sec`
  }
];