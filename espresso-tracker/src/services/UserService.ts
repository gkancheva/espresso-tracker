import { useState } from "react";
import { finalize } from "rxjs";
import { LoginRequest, loginUserApi, RegisterRequest, registerUserApi } from "../config/user.api";
import { useAuth } from "../services/AuthService";

export const useUserAuthentication = (
  onSuccess: () => void,
  onError: (err: string) => void) => {
  const [isFetching, setIsFetching] = useState(false);

  const { saveUser } = useAuth();

  const sendLogin = (request: LoginRequest) => {
    setIsFetching(true);

    const subscription = loginUserApi(request)
      .pipe(finalize(() => setIsFetching(false)))
      .subscribe({
        next: (response) => {
          const user = {
            authData: window.btoa(request.username + ':' + request.password),
            username: response.data.username
          };
          saveUser(user);
          onSuccess();
        },
        error: (err) => onError(err.message)
    });

    return () => subscription.unsubscribe();
  }

  const sendRegister = (request: RegisterRequest) => {
    setIsFetching(true);

    const subscription = registerUserApi(request)
      .pipe(finalize(() => setIsFetching(false)))
      .subscribe({
        next: () => onSuccess(),
        error: (err) => onError(err.message)
      });

    return () => subscription.unsubscribe();
  }

  return { isFetching, sendLogin, sendRegister }

}