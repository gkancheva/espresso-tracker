import { createBakeryApi, getBakeryApi, getBakeriesApi } from "../config/bakery.api";

export interface BakeryRequest {
  name: string;
  address: string;
  phone: string;
  webSite: string;
  imgSource?: string;
}

export const useBakeryService = (onSuccess: (data: any) => void, onFailure: (errMessage: string) => void) => {

  const getBakeryList = () => {
    const subscription = getBakeriesApi().subscribe({
      next: (response) => onSuccess(response.data),
      error: (err) => onFailure(err.message)
    });

    return () => subscription.unsubscribe();
  }

  const sendCreateBakery = (request: BakeryRequest) => {
    const subscription = createBakeryApi(request).subscribe({
      next: (response) => onSuccess(response.data),
      error: (err) => onFailure(err.message)
    });
    return () => subscription.unsubscribe();
  }

  const getBakery = (id: number) => {
    const subscription = getBakeryApi(id).subscribe({
      next: (response) => onSuccess(response.data),
      error: (err) => onFailure(err.message)
    });
    return () => subscription.unsubscribe();
  }

  return { getBakeryList, sendCreateBakery, getBakery }
}