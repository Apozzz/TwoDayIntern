import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProductListComponent } from '@components/product-list/product-list.component';
import { ProfitComponent } from '@components/profit/profit.component';
import { PurchaseComponent } from '@components/purchase/purchase.component';
import { ReportComponent } from '@components/report/report.component';
import { profitResolver } from '@resolvers/profit.resolver';
import { productResolver } from './resolvers/product.resolver';

const routes: Routes = [
  { path: 'products', component: ProductListComponent, resolve: { products: productResolver } },
  { path: 'reports', component: ReportComponent },
  { path: 'purchases/:productId', component: PurchaseComponent, resolve: { products: productResolver } },
  { path: 'purchases', component: PurchaseComponent, resolve: { products: productResolver } },
  { path: 'graphs/:year/:month', component: ProfitComponent, resolve: { profitData: profitResolver } },
  { path: 'graphs/:year', component: ProfitComponent, resolve: { profitData: profitResolver } },
  { path: 'graphs', pathMatch: 'full', redirectTo: `/graphs/${new Date().getFullYear()}` },
  { path: '', redirectTo: '/products', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }