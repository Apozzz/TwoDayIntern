import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '@environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ReportService {
  
  private baseUrl: string = `${environment.apiBaseUrl}/reports/purchases/csv`;

  constructor(private http: HttpClient) { }

  downloadCsvReport(dateTime: string): Observable<Blob> {
    const fullUrl = `${this.baseUrl}?dateTime=${dateTime}`;
    return this.http.get(fullUrl, { responseType: 'blob' });
  }
  
}