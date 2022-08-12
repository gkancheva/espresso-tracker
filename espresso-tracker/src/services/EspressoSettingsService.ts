import { EspressoSettingRequest} from "../models/EspressoSetting";
import {createEspressoSettingApi, getEspressoSettingsApi} from "../config/espresso.api";

export const useEspressoSettingsService = (
  onSuccess: (data: any) => void,
  onFailure: (err: string) => void) => {

  const getEspressoSet = () => {
    const subscription = getEspressoSettingsApi()
      .subscribe({
        next: (response) => onSuccess(response.data),
        error: (err) => onFailure(err.message)
      });
    return () => subscription.unsubscribe();
  }

  const createEspressoSetting = (request: EspressoSettingRequest) => {
    const subscription = createEspressoSettingApi(request).subscribe({
      next: (response) => onSuccess(response.data),
      error: (err) => onFailure(err.message)
    });
    return () => subscription.unsubscribe();
  }

  return { getEspressoSet, createEspressoSetting }
}