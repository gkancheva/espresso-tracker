import { useParams } from "react-router";
import * as React from "react";
import Typography from "@mui/material/Typography";
import { useBakeryService } from "../services/BakeryService";
import { useEffect, useState } from "react";
import { BakeryWithCoffees } from "../models/Bakery";
import { BakeryCard } from "../components/BakeryCard";
import { CoffeeTable} from "../components/CoffeeTableElements";
import { GlobalNotificationProps } from "../components/Notification";

export const BakeryDetails = ({ showNotification }: GlobalNotificationProps) => {
  const { id } = useParams();

  const [bakeryWithCoffees, setBakeryWithCoffees] = useState<BakeryWithCoffees>();

  const { getBakery } = useBakeryService(
    (data) => setBakeryWithCoffees(data),
    (err) => showNotification(err, 'error')
  );

  useEffect(() => {
  }, [bakeryWithCoffees]);

  useEffect(() => {
    if (id) {
      const bakeryId = Number.parseInt(id);
      getBakery(bakeryId);
    }
  }, []);

  return (
    <div style={{ margin: 10 }}>
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
  );
}