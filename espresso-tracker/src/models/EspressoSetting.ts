import { Coffee } from "../models/Coffee";
import { CoffeeTool } from "../services/CoffeeToolsService";

export interface EspressoSetting {
  id: number;
  coffee: Coffee;
  coffeeMachine: CoffeeTool;
  grinder: CoffeeTool;
  dose: number;
  grindingFineness: string;
  brewingTemperature: number;
  brewingPressure: number;
  volume: number;
  extractDurationSec: number;
}

export interface EspressoSettingRequest {
  coffeeId: number;
  dose: number;
  grindingFineness: string;
  brewingTemperature: number;
  brewingPressure: number;
  volume: number;
  extractDurationSec: number;
}
