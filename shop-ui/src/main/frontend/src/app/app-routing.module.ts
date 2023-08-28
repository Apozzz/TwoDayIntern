import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ProductListComponent } from '@components/product-list/product-list.component';
import { PurchaseComponent } from '@components/purchase/purchase.component';
import { productResolver } from './resolvers/product.resolver';

const routes: Routes = [
  { path: 'products', component: ProductListComponent, resolve: { products: productResolver } },
  { path: 'purchase', component: PurchaseComponent },
  { path: 'purchase/:productId', component: PurchaseComponent },
  { path: '', redirectTo: '/products', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }