import { useState } from "react";
import { finalize } from "rxjs";
import { EspressoSetting } from "../models/EspressoSetting";
import { useEspressoApi } from "../config/espresso.api";

interface EspressoSettingsService {
  getEspressoSet: () => void;
  isFetching: boolean;
  data: EspressoSetting[];
}

export const useEspressoSettingsService = (): EspressoSettingsService => {
  const { getEspressoSettings } = useEspressoApi();

  const [isFetching, setIsFetching] = useState(false);
  const [data, setData] = useState<EspressoSetting[]>([] as EspressoSetting[]);

  const getEspressoSet = () => {
    setIsFetching(true);
    const subscription = getEspressoSettings()
      .pipe(finalize(() => setIsFetching(false)))
      .subscribe({
        next: (response) => setData(response.data),
        error: (err) => console.log("error: " + err)
      });

    return () => subscription.unsubscribe();
  }

  return { isFetching, getEspressoSet, data }
}