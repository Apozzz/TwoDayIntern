import { Component, OnInit, } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProductService, ProductDto } from '@services/product.service';
import { ToastrService } from 'ngx-toastr';

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

    if (productId != null) {
      this.selectedProductId = +productId;
      this.updateSelectedProduct();
    }

    this.productService.getProducts().subscribe({
      next: data => {
        this.products = data;
        this.updateSelectedProduct();
      },
      error: error => {
        let errorMessage = error?.message || 'Something went wrong!';
        this.toastr.error(errorMessage, 'Error');
      }
    });
  }

  onProductSelect(productId: number): void {
    this.selectedProductId = productId;
    this.updateSelectedProduct();
  }

  updateSelectedProduct(): void {
    this.selectedProduct = this.products.find(p => p.id === this.selectedProductId) || null;
    if (this.selectedProduct && this.selectedProduct.quantity <= 0) {
      this.productAvailableForPurchase = false;
    } else {
      this.productAvailableForPurchase = true;
    }
  }

  executePurchase(): void {
    if (this.selectedProductId !== null) {
      this.productService.purchaseProduct(this.selectedProductId, this.purchaseQuantity)
        .subscribe({
          next: () => {
            this.purchaseSuccess = true;
            this.ngOnInit();
          },
          error: (error) => {
            this.purchaseSuccess = false;
            let errorMessage = error?.message || 'Something went wrong!';
            this.toastr.error(errorMessage, 'Error');
          },
        });
    }
  }
}
