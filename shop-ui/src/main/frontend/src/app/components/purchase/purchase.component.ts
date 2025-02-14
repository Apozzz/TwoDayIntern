import { Component, OnDestroy, OnInit, } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProductService } from '@services/product.service';
import { ToastrService } from 'ngx-toastr';
import { Subscription } from 'rxjs';
import { ProductDto } from 'src/app/shared/models/product-dto.interface';

@Component({
  selector: 'app-purchase',
  templateUrl: './purchase.component.html',
  styleUrls: ['./purchase.component.less']
})
export class PurchaseComponent implements OnInit, OnDestroy {

  products: ProductDto[] = [];
  selectedProductId: number | null = null;
  selectedProduct: ProductDto | null = null;
  purchaseQuantity: number = 1;
  purchaseSuccess: boolean | null = null;
  productAvailableForPurchase: boolean = true;
  private subscription = new Subscription();

  constructor(private productService: ProductService, private route: ActivatedRoute, private toastr: ToastrService) { }

  ngOnInit(): void {
    const productId = this.route.snapshot.paramMap.get('productId');
    const products = this.route.snapshot.data['products'];
    this.products = products.filter((p: ProductDto) => p.quantity > 0);

    if (productId != null) {
      this.selectedProductId = +productId;
      this.updateSelectedProduct();
    }
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
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
      this.subscription.add(
        this.productService.purchaseProduct(this.selectedProductId, this.purchaseQuantity)
          .subscribe({
            next: () => {
              const productIndex = this.products.findIndex(p => p.id === this.selectedProductId);

              if (productIndex !== -1) {
                this.products[productIndex].quantity -= this.purchaseQuantity;
                this.products = this.products.filter((p: ProductDto) => p.quantity > 0);
                this.updateSelectedProduct();
              }

              this.purchaseSuccess = true;
              this.toastr.success('Purchase was successful!', 'Success');
            },
            error: (error) => {
              this.purchaseSuccess = false;
              this.toastr.error('There was an error when purchasing selected Product', 'Error');
            },
          }));
    }
  }
}
