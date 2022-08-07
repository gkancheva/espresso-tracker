import * as React from 'react';
import './App.css';

import { NavigationAppBar } from './components/NavigationAppBar'
import Container from '@mui/material/Container';
import { EspressoSettingsTable } from "./components/EspressoSettingsTable";
import { Route, BrowserRouter as Router, Routes } from "react-router-dom";
import { Login } from "./components/Login";
import { Register } from "./components/Register";
import { GuardedRoute } from "./components/GuardedRoute";
import { AddNewEspressoShot } from "./components/AddNewEspressoShot";
import { BakeryList } from "./components/BakeryList";
import { AddNewCoffeeBakery } from "./components/AddNewCoffeeBakery";

export default function App () {
  return (
    <Router>
      <Container>
        <NavigationAppBar />
        <Routes>
          <Route
            path={'/espresso-list'}
            element={
              <GuardedRoute component={<EspressoSettingsTable/>} />
            }
          />
          <Route
            path={'/add-new-espresso-shot'}
            element={
              <GuardedRoute component={<AddNewEspressoShot />} />
            }
          />
          <Route
            path={'/add-new-bakery'}
            element={
              <GuardedRoute component={<AddNewCoffeeBakery />} />
            }
          />
          <Route path={'/bakeries'} element={<BakeryList />} />
          <Route path={'/login'} element={<Login />} />
          <Route path={'/register'} element={<Register />} />
        </Routes>
      </Container>
    </Router>

  );
}
