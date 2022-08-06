import {TextField} from "@mui/material";
import Button from "@mui/material/Button";
import Paper from "@mui/material/Paper";
import {useEffect, useState} from "react";
import {useUserAuthentication} from "../services/UserService";

export const Login = () => {

  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [emailError, setEmailError] = useState(false);
  const [passwordError, setPasswordError] = useState(false);
  const [btnDisabled, setBtnDisabled] = useState(true);

  const { sendLogin } = useUserAuthentication(
    () => console.log("logged in!"),
    (err) => console.log(err)
  );

  const checkString = (str: string) => str && str.trim().length > 0;

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
    setPasswordError(false);
    setPassword(input);
  }

  const handleLogin = () => {
    const request = {
      'email': email,
      'password': password
    }

    sendLogin(request);

  }

  useEffect(() => {
    if (checkString(email) && checkString(password)) {
      setBtnDisabled(false);
    } else {
      setBtnDisabled(true);
    }
  }, [email, password]);

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
  );
}