import { BakeryRequest, useBakeryApi } from "../config/bakery.api";

export const useBakeryService = (onSuccess: (data: any) => void, onFailure: (errMessage: string) => void) => {
  const { getBakeries, createBakery } = useBakeryApi();

  const getBakeryList = () => {
    const subscription = getBakeries().subscribe({
      next: (response) => onSuccess(response.data),
      error: (err) => onFailure(err.response.data)
    });

    return () => subscription.unsubscribe();
  }

  const sendCreateBakery = (request: BakeryRequest) => {
    const subscription = createBakery(request).subscribe({
      next: (response) => {
        console.log("Response.data: " + response.data)
        onSuccess(response.data)
      },
      error: (err) => onFailure(err.response.data.message)
    });
    return () => subscription.unsubscribe();
  }

  return { getBakeryList, sendCreateBakery }
}