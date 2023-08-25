import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ProductListComponent } from './components/product-list/product-list.component';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from '@material/material.module';
import { PurchaseComponent } from '@components/purchase/purchase.component';
import { FormsModule } from '@angular/forms';
import { ReportComponent } from './components/report/report.component';
import { ProductFilterComponent } from './components/product-filter/product-filter.component';
import { ProductSortComponent } from './components/product-sort/product-sort.component';
import { GenericFilterComponent } from './components/generic-filter/generic-filter.component';
import { GenericSortComponent } from './components/generic-sort/generic-sort.component';

@NgModule({
  declarations: [
    AppComponent,
    ProductListComponent,
    PurchaseComponent,
    ReportComponent,
    ProductFilterComponent,
    ProductSortComponent,
    GenericFilterComponent,
    GenericSortComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MaterialModule,
    FormsModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
