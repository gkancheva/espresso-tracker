import * as React from 'react';
import './App.css';

import { NavigationAppBar } from './components/NavigationAppBar'
import Container from '@mui/material/Container';
import { AlertColor } from "@mui/material/Alert";
import { EspressoSettingsTable } from "./components/EspressoSettingsTable";
import { Route, BrowserRouter as Router, Routes } from "react-router-dom";
import { Login } from "./components/Login";
import { Register } from "./components/Register";
import { GuardedRoute } from "./components/GuardedRoute";
import { AddNewEspressoShot } from "./components/AddNewEspressoShot";
import { BakeryList } from "./components/BakeryList";
import { AddNewCoffeeBakery } from "./components/AddNewCoffeeBakery";
import { AddNewCoffee } from "./components/AddNewCoffee";
import { BakeryDetails } from "./components/BakeryDetails";
import { Notification } from "./components/Notification";
import { useState } from "react";

const DEFAULT_NOTIFICATION_TYPE = 'success';

export default function App () {
  const [message, setMessage] = useState("");
  const [notificationVisible, setNotificationVisible] = useState(false);
  const [notificationType, setNotificationType] = useState<AlertColor>();

  const updateNotification = (message: string, type?: AlertColor) => {
    setMessage(message);
    setNotificationType(type? type : DEFAULT_NOTIFICATION_TYPE);
    setNotificationVisible(true);
  }

  return (
    <Router>
      <Container>
        <NavigationAppBar showNotification={updateNotification} />
        {
          notificationVisible &&
          <Notification
            type={notificationType}
            message={message}
            isVisible={notificationVisible}
            notifyIsVisible={setNotificationVisible} />
        }
        <Routes>
          <Route
            path={'/espresso-list'}
            element={
              <GuardedRoute component={<EspressoSettingsTable showNotification={updateNotification} />} />
            }
          />
          <Route
            path={'/add-new-espresso-shot'}
            element={
              <GuardedRoute component={<AddNewEspressoShot showNotification={updateNotification} />} />
            }
          />
          <Route
            path={'/add-new-bakery'}
            element={
              <GuardedRoute component={<AddNewCoffeeBakery showNotification={updateNotification} />} />
            }
          />
          <Route
            path={'/add-new-coffee'}
            element={
              <GuardedRoute component={<AddNewCoffee showNotification={updateNotification} />} />
            }
          />
          <Route path={'/bakeries/:id'} element={<BakeryDetails showNotification={updateNotification} />}/>
          <Route path={'/bakeries'} element={<BakeryList showNotification={updateNotification} />} />
          <Route path={'/login'} element={<Login showNotification={updateNotification} />} />
          <Route path={'/register'} element={<Register showNotification={updateNotification} />} />
        </Routes>
      </Container>
    </Router>

  );
}
