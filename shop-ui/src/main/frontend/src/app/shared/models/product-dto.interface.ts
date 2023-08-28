export interface ProductDto {
  id: number;
  name: string;
  description?: string;
  price: number;
  finalPrice: number;
  quantity: number;
  quantityToBuy?: number;
}