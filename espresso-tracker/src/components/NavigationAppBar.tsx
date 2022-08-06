import * as React from 'react';
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import IconButton from '@mui/material/IconButton';
import MenuIcon from '@mui/icons-material/Menu';
import {useAuth} from "../services/AuthService";

const { isAuthenticated } = useAuth();

export const NavigationAppBar = () => {

  return (
    <AppBar position='static'>
      <Toolbar>
        <IconButton
          size='large'
          edge='start'
          color='inherit'
          aria-label='menu'
          sx={{mr: 2}}
          onClick={() => console.log('Burger button clicked!')}>
          <MenuIcon/>
        </IconButton>
        { isAuthenticated() &&
          <Typography variant='h6' component='div' sx={{flexGrow: 1}}>
            <Button href={'/espresso-list'} color='inherit'>
              Espresso-list
            </Button>
          </Typography>
        }
        { !isAuthenticated() &&
          <>
            <Button href={'/login'} color='inherit'>
              Login
            </Button>
            <Button href={'/register'} color='inherit'>
              Register
            </Button>
          </>
        }
      </Toolbar>
    </AppBar>
  );
}
