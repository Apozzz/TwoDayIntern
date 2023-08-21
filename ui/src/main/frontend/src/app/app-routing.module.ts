import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ProductListComponent } from '@components/product-list/product-list.component';
import { ProfitGraphComponent } from '@components/profit-graph/profit-graph.component';
import { PurchaseComponent } from '@components/purchase/purchase.component';
import { ReportComponent } from '@components/report/report.component';

const routes: Routes = [
  { path: 'products', component: ProductListComponent },
  { path: 'reports', component: ReportComponent },
  { path: 'purchases', component: PurchaseComponent },
  { path: 'purchases/:productId', component: PurchaseComponent },
  { path: 'graphs', component: ProfitGraphComponent },
  { path: '', redirectTo: '/products', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }