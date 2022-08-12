import { useState } from "react";
import { finalize } from "rxjs";
import { EspressoSettingRequest} from "../models/EspressoSetting";
import { createEspressoSettingApi, getEspressoSettingsApi } from "../config/espresso.api";

export const useEspressoSettingsService = (
  onSuccess: (data: any) => void,
  onFailure: (err: string) => void) => {
  const [fetching, setIsFetching] = useState(false);

  const getEspressoSet = () => {
    setIsFetching(true);
    const subscription = getEspressoSettingsApi()
      .pipe(finalize(() => setIsFetching(false)))
      .subscribe({
        next: (response) => onSuccess(response.data),
        error: (err) => onFailure(err.message)
      });
    return () => subscription.unsubscribe();
  }

  const createEspressoSetting = (request: EspressoSettingRequest) => {
    setIsFetching(true);
    const subscription = createEspressoSettingApi(request)
      .pipe(finalize(() => setIsFetching(false)))
      .subscribe({
        next: (response) => onSuccess(response.data),
        error: (err) => onFailure(err.message)
      });
    return () => subscription.unsubscribe();
  }

  return { fetching, getEspressoSet, createEspressoSetting }
}