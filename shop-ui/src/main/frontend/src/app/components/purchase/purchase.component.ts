import { Component, OnInit, } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProductService } from '@services/product.service';
import { ToastrService } from 'ngx-toastr';
import { ProductDto } from 'src/app/shared/models/product-dto.interface';

@Component({
  selector: 'app-purchase',
  templateUrl: './purchase.component.html',
  styleUrls: ['./purchase.component.less']
})
export class PurchaseComponent implements OnInit {

  products: ProductDto[] = [];
  selectedProductId: number | null = null;
  selectedProduct: ProductDto | null = null;
  purchaseQuantity: number = 1;
  purchaseSuccess: boolean | null = null;
  productAvailableForPurchase: boolean = true;

  constructor(private productService: ProductService, private route: ActivatedRoute, private toastr: ToastrService) { }

  ngOnInit(): void {
    const productId = this.route.snapshot.paramMap.get('productId');
    const products = this.route.snapshot.data['products'];

    if (productId != null) {
      this.selectedProductId = +productId;
      this.updateSelectedProduct();
    }

    this.products = products;
  }

  onProductSelect(productId: number): void {
    this.selectedProductId = productId;
    this.updateSelectedProduct();
  }

  updateSelectedProduct(): void {
    this.selectedProduct = this.products.find(p => p.id === this.selectedProductId) || null;
    if (this.selectedProduct && this.selectedProduct.quantity <= 0 || !this.selectedProduct) {
      this.productAvailableForPurchase = false;
    } else {
      this.productAvailableForPurchase = true;
    }
  }

  purchase(): void {
    if (this.selectedProductId !== null) {
      this.productService.purchaseProduct(this.selectedProductId, this.purchaseQuantity)
        .subscribe({
          next: () => {
            const productIndex = this.products.findIndex(p => p.id === this.selectedProductId);

            if (productIndex !== -1) {
              this.products[productIndex].quantity -= this.purchaseQuantity;
              this.updateSelectedProduct();
            }
            
            this.purchaseSuccess = true;
            this.toastr.success('Purchase was successful!', 'Success');
          },
          error: (error) => {
            this.purchaseSuccess = false;
            this.toastr.error(error.data, 'Error');
          },
        });
    }
  }
}
