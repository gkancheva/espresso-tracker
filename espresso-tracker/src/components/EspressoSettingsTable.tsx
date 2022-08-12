import * as React from 'react';
import { useNavigate } from "react-router";
import { useEffect, useState } from "react";
import Button from "@mui/material/Button";
import AddIcon from '@mui/icons-material/Add';
import { DataGrid } from "@mui/x-data-grid";
import { useEspressoSettingsService } from "../services/EspressoSettingsService";
import { EspressoSetting } from "../models/EspressoSetting";
import { GlobalNotificationProps } from "../components/Notification";
import { espressoSettingsColumns } from "../components/EspressoSettingsColumns";

export const EspressoSettingsTable = ({ showNotification }: GlobalNotificationProps) => {
  const [data, setData] = useState<EspressoSetting[]>([] as EspressoSetting[]);

  const { fetching, getEspressoSet } = useEspressoSettingsService(
    (data) => setData(data),
    (err) => showNotification(err, 'error')
  );

  const navigate = useNavigate();

  useEffect(() => {
    getEspressoSet();
  }, []);

  return (
    <>
      <Button
        onClick={() => navigate('/add-new-espresso-shot')}
        size='small'
        style={{ margin: 4 }}
        variant="outlined"
        endIcon={<AddIcon />}>
        Add new shot
      </Button>
      <div>
        <DataGrid
          density='compact'
          autoHeight
          loading={fetching}
          rows={data}
          columns={espressoSettingsColumns}
          pageSize={10}
          rowsPerPageOptions={[10]} />
      </div>
    </>
  );
}