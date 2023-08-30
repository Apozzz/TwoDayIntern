import { Injectable } from '@angular/core';
import { ToastrService } from 'ngx-toastr';

@Injectable({
  providedIn: 'root'
})
export class LocalStorageService {

  constructor(private toastr: ToastrService) { }

  set(key: string, value: any): void {
    try {
      localStorage.setItem(key, JSON.stringify(value));
    } catch (error) {
      this.toastr.error(`Could not set the localStorage item: ${error}`, 'Error');
    }
  }

  get<T>(key: string): T | null {
    try {
      const serializedValue = localStorage.getItem(key);
      return serializedValue ? JSON.parse(serializedValue) as T : null;
    } catch (error) {
      this.toastr.error(`Could not get the localStorage item: ${error}`, 'Error');
      return null;
    }
  }

  remove(key: string): void {
    try {
      localStorage.removeItem(key);
    } catch (error) {
      this.toastr.error(`Could not remove the localStorage item: ${error}`, 'Error');
    }
  }

  clear(): void {
    try {
      localStorage.clear();
    } catch (error) {
      this.toastr.error(`Could not clear the localStorage: ${error}`, 'Error');
    }
  }
}