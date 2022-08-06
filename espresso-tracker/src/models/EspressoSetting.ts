import { Coffee } from "../models/Coffee";

export interface EspressoSetting {
  id: number;
  coffee: Coffee;
  dose: number;
  grindingFineness: string;
  brewingTemperature: number;
  brewingPressure: number;
  volume: number;
}