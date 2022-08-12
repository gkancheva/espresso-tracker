import * as React from 'react';
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import Typography from "@mui/material/Typography";
import { useAuth } from "../services/AuthService";
import { CustomNavButton, ViewAccessType } from "../components/CustomNavButton";
import { MenuButton } from "../components/MenuButton";
import { GlobalNotificationProps } from "../components/Notification";

export const NavigationAppBar = ({ showNotification }: GlobalNotificationProps) => {
  const { logoutUser } = useAuth();

  return (
    <AppBar position='static'>
      <Toolbar>
        <MenuButton showNotification={showNotification} />
        <Typography style={{ flex: 1 }}>
          <CustomNavButton href='/bakeries' text={'Bakeries'} />
          <CustomNavButton
            text={'Espresso list'}
            href={'/espresso-list'}
            viewAccess={ViewAccessType.AUTHENTICATED} />
        </Typography>
        <CustomNavButton
          href={'/login'}
          text={'Logout'}
          onClick={() => logoutUser()}
          viewAccess={ViewAccessType.AUTHENTICATED} />
        <CustomNavButton
          href={'/login'}
          text={'Login'}
          viewAccess={ViewAccessType.UNAUTHENTICATED} />
        <CustomNavButton
          href={'/register'}
          text={'Register'}
          viewAccess={ViewAccessType.UNAUTHENTICATED} />
      </Toolbar>
    </AppBar>
  );
}
