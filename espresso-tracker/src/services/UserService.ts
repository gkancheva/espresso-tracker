import { useState } from "react";
import {LoginRequest, loginUser, RegisterRequest, registerUser} from "../config/api";
import { finalize } from "rxjs";
import { useAuth } from "../services/AuthService";

export const useUserAuthentication = (
  onSuccess: () => void,
  onError: (err: string) => void) => {
  const [isFetching, setIsFetching] = useState(false);

  const { saveUser } = useAuth();

  const sendLogin = (request: LoginRequest) => {
    setIsFetching(true);

    const subscription = loginUser(request)
      .pipe(finalize(() => setIsFetching(false)))
      .subscribe({
        next: (response) => {
          const user = {
            authData: window.btoa(request.email + ':' + request.password),
            username: response.data.username
          };
          saveUser(user);
          onSuccess();
        },
        error: (err) => onError(err)
    });

    return () => subscription.unsubscribe();
  }

  const sendRegister = (request: RegisterRequest) => {
    setIsFetching(true);

    const subscription = registerUser(request)
      .pipe(finalize(() => setIsFetching(false)))
      .subscribe({
        next: () => onSuccess(),
        error: (err) => onError(err)
      });

    return () => subscription.unsubscribe();
  }

  return { isFetching, sendLogin, sendRegister }

}