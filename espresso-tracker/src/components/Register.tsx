import * as React from "react";
import { useNavigate } from "react-router";
import { useEffect, useState } from "react";
import { TextField } from "@mui/material";
import Button from "@mui/material/Button";
import Paper from "@mui/material/Paper";
import { useUserAuthentication } from "../services/UserService";
import { checkInputIsValid } from "../utils/Util";
import { GlobalNotificationProps } from "../components/Notification";

const MAX_LENGTH = 20;

export const Register = ({ showNotification }: GlobalNotificationProps) => {
  const navigate = useNavigate();

  const [email, setEmail] = useState('');
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [emailError, setEmailError] = useState(false);
  const [passwordError, setPasswordError] = useState(false);
  const [confirmPasswordError, setConfirmPasswordError] = useState(false);
  const [usernameError, setUsernameError] = useState(false);
  const [btnDisabled, setBtnDisabled] = useState(true);

  const { sendRegister } = useUserAuthentication(
    () => {
      showNotification('Successfully registered', 'success');
      navigate("/login");
    },
    (errMessage) => showNotification(errMessage, 'error')
  );

  const handleEmailChange = (input: string) => {
    if (!checkInputIsValid(input, MAX_LENGTH)) {
      setEmailError(true);
      return;
    }
    setEmailError(false);
    setEmail(input);
  }

  const handlePasswordChange = (input: string) => {
    if (!checkInputIsValid(input, MAX_LENGTH)) {
      setPasswordError(true);
      return;
    }
    setPasswordError(false);
    setPassword(input);
  }

  const handleConfirmPasswordChange = (input: string) => {
    if (password !== input) {
      setConfirmPasswordError(true);
      return;
    }
    setConfirmPasswordError(false);
    setConfirmPassword(input);
  }

  const handleUsernameChange = (input: string) => {
    if (!checkInputIsValid(input, MAX_LENGTH)) {
      setUsernameError(true);
      return;
    }
    setUsernameError(false);
    setUsername(input);
  }

  const handleRegister = () => {
    const request = {
      'email': email,
      'password': password,
      'username': username
    }

    sendRegister(request);
  }

  useEffect(() => {
    if (checkInputIsValid(email, MAX_LENGTH) &&
      checkInputIsValid(password, MAX_LENGTH) &&
      checkInputIsValid(username, MAX_LENGTH) &&
      confirmPassword === password) {
      setBtnDisabled(false);
    } else {
      setBtnDisabled(true);
    }
  }, [email, password, username, confirmPassword]);

  return (
    <Paper style={{padding: 20, width: 500, margin: '20px auto'}}>
      <TextField
        error={emailError}
        label='Email'
        type='text'
        id='email'
        fullWidth
        required
        variant={'standard'}
        onChange={(event) => handleEmailChange(event.target.value)}
        helperText={emailError && 'Email cannot be empty'} />
      <TextField
        error={usernameError}
        label='Username'
        type='text'
        id='username'
        fullWidth
        required
        variant={'standard'}
        onChange={(event) => handleUsernameChange(event.target.value)}
        helperText={usernameError && 'Username should have at least 3 characters length'} />
      <TextField
        error={passwordError}
        label='Password'
        type='password'
        id='password'
        fullWidth
        required
        variant={'standard'}
        onChange={(event) => handlePasswordChange(event.target.value)}
        helperText={passwordError && 'Password should have at least 3 characters length'}/>
      <TextField
        error={confirmPasswordError}
        label='Confirm password'
        type='password'
        id='confirmPassword'
        fullWidth
        required
        variant={'standard'}
        onChange={(event) => handleConfirmPasswordChange(event.target.value)}
        helperText={confirmPasswordError && 'Password does not match'}/>
      <Button
        style={{ marginTop: 8 }}
        disabled={btnDisabled}
        variant={'contained'}
        type="submit"
        fullWidth
        onClick={() => handleRegister()}>
        Register
      </Button>
      <Button style={{ marginTop: 8 }} href={'/login'} type={'button'} fullWidth>
        Log in
      </Button>
    </Paper>
  );
}