import { useParams } from "react-router";
import Typography from "@mui/material/Typography";
import { useBakeryService } from "../services/BakeryService";
import { useEffect, useState } from "react";
import { BakeryWithCoffees } from "../models/Bakery";
import { BakeryCard } from "../components/BakeryCard";
import * as React from "react";
import { CoffeeTable} from "../components/CoffeeTableElements";
import { Notification } from "../components/Notification";

export const BakeryDetails = () => {
  const { id } = useParams();

  const [bakeryWithCoffees, setBakeryWithCoffees] = useState<BakeryWithCoffees>();
  const [notificationIsVisible, setNotificationIsVisible] = useState(false);
  const [errorMessage, setErrorMessage] = useState<string>('');

  const { getBakery } = useBakeryService(
    (data) => setBakeryWithCoffees(data),
    (err) => {
      console.log(JSON.stringify(err));
      setErrorMessage(err);
      setNotificationIsVisible(true);
    });

  useEffect(() => {
  }, [bakeryWithCoffees]);

  useEffect(() => {
    if (id) {
      const bakeryId = Number.parseInt(id);
      getBakery(bakeryId);
    }
  }, []);

  return (
    <>
      { notificationIsVisible &&
        <Notification
          isVisible={notificationIsVisible}
          message={errorMessage}
          notifyIsVisible={setNotificationIsVisible} />
      }
      <div style={{ margin: 4 }}>
        {
          bakeryWithCoffees &&
          <BakeryCard bakery={bakeryWithCoffees.bakery} hideLearnMore={true}>
            {
              bakeryWithCoffees.coffees.length > 0 ?
                <CoffeeTable coffees={bakeryWithCoffees.coffees} /> :
                <Typography variant="body2" color="text.secondary" m={2}>
                  {bakeryWithCoffees.bakery.name} does not offer yet any coffees
                </Typography>
            }
          </BakeryCard>
        }
      </div>
    </>
  );
}