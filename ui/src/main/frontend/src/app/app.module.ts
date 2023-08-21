import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ProductListComponent } from '@components/product-list/product-list.component';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from '@material/material.module';
import { PurchaseComponent } from '@components/purchase/purchase.component';
import { FormsModule } from '@angular/forms';
import { ReportComponent } from '@components/report/report.component';
import { ProductFilterComponent } from '@components/product-filter/product-filter.component';
import { ProductSortComponent } from '@components/product-sort/product-sort.component';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { LanguageComponent } from '@components/language/language.component';
import { ProfitGraphComponent } from '@components/profit-graph/profit-graph.component';
import { NgChartsModule } from 'ng2-charts';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

@NgModule({
  declarations: [
    AppComponent,
    ProductListComponent,
    PurchaseComponent,
    ReportComponent,
    ProductFilterComponent,
    ProductSortComponent,
    LanguageComponent,
    ProfitGraphComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MaterialModule,
    FormsModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    }),
    NgChartsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
