import { BakeryRequest, useBakeryApi } from "../config/bakery.api";

export const useBakeryService = (onSuccess: (data: any) => void, onFailure: (errMessage: string) => void) => {
  const { getBakeries, createBakery, fetchBakery } = useBakeryApi();

  const getBakeryList = () => {
    const subscription = getBakeries().subscribe({
      next: (response) => onSuccess(response.data),
      error: (err) => onFailure(err.message)
    });

    return () => subscription.unsubscribe();
  }

  const sendCreateBakery = (request: BakeryRequest) => {
    const subscription = createBakery(request).subscribe({
      next: (response) => onSuccess(response.data),
      error: (err) => onFailure(err.message)
    });
    return () => subscription.unsubscribe();
  }

  const getBakery = (id: number) => {
    const subscription = fetchBakery(id).subscribe({
      next: (response) => onSuccess(response.data),
      error: (err) => onFailure(err.message)
    });
    return () => subscription.unsubscribe();
  }

  return { getBakeryList, sendCreateBakery, getBakery }
}