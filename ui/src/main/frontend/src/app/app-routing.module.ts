import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ProductListComponent } from '@components/product-list/product-list.component';
import { ProfitComponent } from '@components/profit/profit.component';
import { PurchaseComponent } from '@components/purchase/purchase.component';
import { ReportComponent } from '@components/report/report.component';
import { profitResolver } from '@resolvers/profit.resolver';

const routes: Routes = [
  { path: 'products', component: ProductListComponent },
  { path: 'reports', component: ReportComponent },
  { path: 'purchases', component: PurchaseComponent },
  { path: 'purchases/:productId', component: PurchaseComponent },
  { path: 'graphs', pathMatch: 'full', redirectTo: `/graphs/${new Date().getFullYear()}` },
  { path: 'graphs/:year', component: ProfitComponent, resolve: { profitData: profitResolver } },
  { path: 'graphs/:year/:month', component: ProfitComponent, resolve: { profitData: profitResolver } },
  { path: '', redirectTo: '/products', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }