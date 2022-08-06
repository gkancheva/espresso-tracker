import {TextField} from "@mui/material";
import Button from "@mui/material/Button";
import Paper from "@mui/material/Paper";
import {useEffect, useState} from "react";
import {useUserAuthentication} from "../services/UserService";

const MIN_LENGHT = 3;

export const Register = () => {

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
    () => console.log("Registered!"),
    (err) => console.log(err)
  );

  const checkString = (str: string) => str && str.trim().length > MIN_LENGHT;

  const handleEmailChange = (input: string) => {
    if (!checkString(input)) {
      setEmailError(true);
      return;
    }
    setPasswordError(false);
    setEmail(input);
  }

  const handlePasswordChange = (input: string) => {
    if (!checkString(input)) {
      setPasswordError(true);
      return;
    }
    if (password !== confirmPassword) {
      setConfirmPasswordError(true);
      setPasswordError(true);
      setConfirmPassword('');
      setPassword('');
      return;
    }
    setPasswordError(false);
    setPassword(input);
  }

  const handleRegister = () => {
    const request = {
      'email': email,
      'password': password,
      'username': username ? username : email
    }

    sendRegister(request);

  }

  useEffect(() => {
    if (checkString(email) && checkString(password)) {
      setBtnDisabled(false);
    } else {
      setBtnDisabled(true);
    }
  }, [email, password, username]);

  return (
    <Paper style={{padding: 20, width: 500, height: '70vh', margin: '20px auto'}}>
      <TextField
        error={emailError}
        style={{marginTop: 8}}
        label='Email'
        type='text'
        id='email'
        fullWidth
        required
        variant={'standard'}
        onChange={(event) => handleEmailChange(event.target.value)}
        helperText={'Email cannot be empty'} />
      <TextField
        style={{marginTop: 8}}
        error={passwordError}
        label='Password'
        type='password'
        id='password'
        fullWidth
        required variant={'standard'}
        onChange={(event) => handlePasswordChange(event.target.value)}
        helperText={'Password cannot be empty'}/>
      <TextField
        style={{marginTop: 8}}
        error={password !== confirmPassword}
        label='Confirm password'
        type='password'
        id='confirmPassword'
        fullWidth
        required variant={'standard'}
        onChange={(event) => handlePasswordChange(event.target.value)}
        helperText={'Password doesnot match'}/>
      <Button
        style={{marginTop: 8}}
        disabled={btnDisabled}
        variant={'contained'}
        type="submit"
        fullWidth
        onClick={() => handleRegister()}>
        Log in
      </Button>
      <Button href={'/register'} style={{marginTop: 8}} type={'button'} fullWidth>
        Register
      </Button>
    </Paper>
  );
}