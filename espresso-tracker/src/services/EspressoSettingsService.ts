import { useState } from "react";
import { getEspressoSettings } from "../config/api";
import { EspressoSetting } from "../models/EspressoSetting";

interface EspressoSettingsService {
  getEspressoSet: () => void;
  isFetching: boolean;
  data: EspressoSetting[];
}

export const useEspressoSettingsService = (): EspressoSettingsService => {
  const [isFetching, setIsFetching] = useState(false);
  const [data, setData] = useState<EspressoSetting[]>([] as EspressoSetting[]);

  const getEspressoSet = () => {
    const subscription = getEspressoSettings().subscribe({
      next: (response) => setData(response.data),
      error: (err) => console.log("error: " + err)
    });

    return () => subscription.unsubscribe();
  }

  return { isFetching, getEspressoSet, data }
}