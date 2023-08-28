import { Injectable } from '@angular/core';
import { MONTH_NAMES } from '@constants/month-names.constants';
import { TranslateService } from '@ngx-translate/core';
import { SortOption } from '../shared/models/sort-options.interface';

@Injectable({
  providedIn: 'root'
})
export class CustomTranslationService {

  constructor(private translate: TranslateService) {}

  translateMonthNames(): string[] {
    return MONTH_NAMES.map(month => this.translate.instant(`MONTHS.${month}`));
  }

  translateAttributes(attributes: { attribute: string; label: string; }[]): { attribute: string; label: string; }[] {
    return this.genericTranslate(attributes, 'label');
  }

  translateSortOptions(sortOptions: SortOption[]): SortOption[] {
    return this.genericTranslate(sortOptions, 'label');
  }

  translateGraphTypes(graphTypes: any[]): any[] {
    return this.genericTranslate(graphTypes, 'viewValue');
  }

  private genericTranslate<T>(items: T[], key: keyof T): T[] {
    return items.map(item => ({
      ...item,
      [key]: this.translate.instant(item[key] as unknown as string)
    }));
  }

}
