import { useCoffeeApi } from "../config/coffee.api";
import { Dayjs } from "dayjs";

export interface CoffeeRequest {
  name: string;
  bakeryId: number;
  roastedOnDate: Dayjs;
  origin: string;
  description?: string;
}

export const useCoffeeService = (onSuccess: (data: any) => void, onFailure: (errMessage: string) => void) => {
  const { createCoffeeApi } = useCoffeeApi();

  const createCoffee = (request: CoffeeRequest) => {
    const subscription = createCoffeeApi(request).subscribe({
      next: (response) => onSuccess(response.data),
      error: (err) => onFailure(err.message)
    });
    return () => subscription.unsubscribe();
  }

  return { createCoffee }

}