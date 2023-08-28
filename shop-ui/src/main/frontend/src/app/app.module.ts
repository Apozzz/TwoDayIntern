import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { HttpClient, HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LanguageComponent } from '@components/language/language.component';
import { ProductListComponent } from '@components/product-list/product-list.component';
import { ProfitGraphComponent } from '@components/profit-graph/profit-graph.component';
import { PurchaseComponent } from '@components/purchase/purchase.component';
import { ReportComponent } from '@components/report/report.component';
import { MaterialModule } from '@material/material.module';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { NgChartsModule } from 'ng2-charts';
import { ToastrModule } from 'ngx-toastr';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { GenericTableComponent } from './components/profit-table/profit-table.component';
import { ProfitComponent } from './components/profit/profit.component';
import { FilterComponent } from '@components/filter/filter.component';
import { SortComponent } from '@components/sort/sort.component';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

@NgModule({
  declarations: [
    AppComponent,
    ProductListComponent,
    PurchaseComponent,
    ReportComponent,
    FilterComponent,
    SortComponent,
    LanguageComponent,
    ProfitGraphComponent,
    GenericTableComponent,
    ProfitComponent,
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
    NgChartsModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot({
      positionClass: 'toast-top-center',
      preventDuplicates: true,
    }),
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
