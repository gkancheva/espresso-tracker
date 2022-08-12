import { useNavigate } from "react-router";
import { TextField } from "@mui/material";
import Button from "@mui/material/Button";
import Paper from "@mui/material/Paper";
import React, { useEffect, useState } from "react";
import { useUserAuthentication } from "../services/UserService";
import { checkInputIsValid } from "../utils/Util";
import { GlobalNotificationProps } from "../components/Notification";

export const Login = ({ showNotification }: GlobalNotificationProps) => {
  const navigate = useNavigate();

  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [usernameError, setUsernameError] = useState(false);
  const [passwordError, setPasswordError] = useState(false);
  const [btnDisabled, setBtnDisabled] = useState(true);

  const { sendLogin } = useUserAuthentication(
    () => navigate('/espresso-list'),
    (err) => showNotification(err, 'error')
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
      <Button onClick={() => navigate('/register')}  style={{marginTop: 8}} type={'button'} fullWidth>
        Register
      </Button>
    </Paper>
  );
}