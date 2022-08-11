import * as React from "react";
import { Grid } from "@mui/material";
import { useNavigate } from "react-router";
import { useEffect, useState } from "react";
import { BakeryCard } from "../components/BakeryCard";
import { useBakeryService } from "../services/BakeryService";
import { Bakery } from "../models/Bakery";
import { Notification } from "../components/Notification";
import { AddButton } from "../components/AddButton";

export const BakeryList = () => {
  const [notificationIsVisible, setNotificationIsVisible] = useState(false);
  const [errMessage, setErrMessage] = useState<string>('');
  const [data, setData] = useState<Bakery[]>([] as Bakery[]);

  const { getBakeryList } = useBakeryService(
    (result) => setData(result),
    (err) => {
      setErrMessage(err);
      setNotificationIsVisible(true);
    }
  );

  const navigate = useNavigate();

  useEffect(() => {
    getBakeryList();
  }, []);

  return (
    <>
      <AddButton
        onClick={() => navigate('/add-new-bakery')}
        text='Add new bakery'/>
      <AddButton
        onClick={() => navigate('/add-new-coffee')}
        text='Add new coffee' />
      { notificationIsVisible && errMessage &&
        <Notification
          isVisible={notificationIsVisible}
          message={errMessage}
          notifyIsVisible={setNotificationIsVisible} />
      }
      <Grid container spacing={2}>
        {
          data.map((bakeryItem: Bakery) => (
            <Grid key={bakeryItem.id} item xs={6}>
              <BakeryCard bakery={bakeryItem} />
            </Grid>
          ))
        }
      </Grid>
    </>
  );

}