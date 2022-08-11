import { useNavigate } from "react-router";
import { TextField } from "@mui/material";
import Button from "@mui/material/Button";
import Paper from "@mui/material/Paper";
import React, { useEffect, useState } from "react";
import { useUserAuthentication } from "../services/UserService";
import { Notification } from "../components/Notification";
import { checkInputIsValid } from "../utils/StringUtil";

export const Login = () => {
  const navigate = useNavigate();

  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [usernameError, setUsernameError] = useState(false);
  const [passwordError, setPasswordError] = useState(false);
  const [btnDisabled, setBtnDisabled] = useState(true);
  const [message, setMessage] = useState('');
  const [notificationIsVisible, setNotificationIsVisible] = useState(false);

  const setNotification = (message: string) => {
    setMessage(message);
    setNotificationIsVisible(true);
  }

  const { sendLogin } = useUserAuthentication(
    () => navigate('/espresso-list'),
    (err) => setNotification(err)
  );

  const handleUsernameChange = (input: string) => {
    if (!checkInputIsValid(input)) {
      setUsernameError(true);
      return;
    }
    setUsernameError(false);
    setUsername(input);
  }

  const handlePasswordChange = (input: string) => {
    if (!checkInputIsValid(input)) {
      setPasswordError(true);
      return;
    }
    setPasswordError(false);
    setPassword(input);
  }

  const handleLogin = () => {
    const request = {
      'username': username,
      'password': password
    }

    sendLogin(request);

  }

  useEffect(() => {
    if (checkInputIsValid(username) && checkInputIsValid(password)) {
      setBtnDisabled(false);
    } else {
      setBtnDisabled(true);
    }
  }, [username, password]);

  return (
    <>
      <Notification
        type={'error'}
        message={message}
        isVisible={notificationIsVisible}
        notifyIsVisible={setNotificationIsVisible} />
      <Paper style={{padding: 20, width: 500, margin: '20px auto'}}>
        <TextField
          error={usernameError}
          style={{marginTop: 8}}
          label='Username'
          type='text'
          id='username'
          fullWidth
          required
          variant={'standard'}
          onChange={(event) => handleUsernameChange(event.target.value)}
          helperText={usernameError && 'Username is empty'} />
        <TextField
          style={{marginTop: 8}}
          error={passwordError}
          label='Password'
          type='password'
          id='password'
          fullWidth
          required variant={'standard'}
          onChange={(event) => handlePasswordChange(event.target.value)}
          helperText={passwordError && 'Password cannot be empty'}/>
        <Button
          style={{marginTop: 8}}
          disabled={btnDisabled}
          variant={'contained'}
          type="submit"
          fullWidth
          onClick={() => handleLogin()}>
          Log in
        </Button>
        <Button href={'/register'} style={{marginTop: 8}} type={'button'} fullWidth>
          Register
        </Button>
      </Paper>
    </>
  );
}