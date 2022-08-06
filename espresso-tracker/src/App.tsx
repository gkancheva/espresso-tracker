import * as React from 'react';
import './App.css';

import { NavigationAppBar } from './components/NavigationAppBar'
import Container from '@mui/material/Container';
import { EspressoSettingsTable } from "./components/EspressoSettingsTable";
import { Route, BrowserRouter as Router, Routes } from "react-router-dom";
import { Login } from "./components/Login";
import { Register } from "./components/Register";

export default function App () {

  return (
    <Router>
      <Container fixed >
        <NavigationAppBar />
        <Routes>
          <Route path={'/espresso-list'} element={<EspressoSettingsTable />} />
          <Route path={'/login'} element={<Login />} />
          <Route path={'/register'} element={<Register />} />
        </Routes>
      </Container>
    </Router>

  );

}
