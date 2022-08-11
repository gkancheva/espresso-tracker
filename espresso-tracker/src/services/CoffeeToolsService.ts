import { createUpdateCoffeeToolApi, getCoffeeToolsApi } from "../config/coffee-tools.api";

export enum CoffeeToolType {
  COFFEE_MACHINE = 'COFFEE_MACHINE',
  GRINDER = 'GRINDER'
}

export interface CoffeeToolRequest {
  coffeeToolType: CoffeeToolType;
  name: string;
}

export interface CoffeeTool {
  id?: number;
  name: string;
}

export interface CoffeeTools {
  coffeeMachine: CoffeeTool;
  grinder: CoffeeTool;
}

export const useCoffeeToolsService = (onSuccess: (data: any) => void, onFailure: (err: string) => void) => {

  const sendCreateUpdateRequest = (request: CoffeeToolRequest) => {
    const subscription = createUpdateCoffeeToolApi(request).subscribe({
      next: (response) => onSuccess(response.data),
      error: (err) => onFailure(err.message)
    });
    return () => subscription.unsubscribe();
  }

  const getCoffeeTools = () => {
    const subscription = getCoffeeToolsApi().subscribe({
      next: (response) => onSuccess(response.data),
      error: (err) => onFailure(err.message)
    });
    return () => subscription.unsubscribe();
  }

  return { getCoffeeTools, sendCreateUpdateRequest };

}