import { ProductDto } from "./product-dto.interface";
import { UserDto } from "./user-dto.interface";

export interface PurchaseDto {
    id: number;
    user: UserDto;
    product: ProductDto;
    quantity: number;
    totalPrice: number;
    timeStamp: string;
}