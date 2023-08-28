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
    return attributes.map(attr => ({
      ...attr,
      label: this.translate.instant(attr.label),
    }));
  }

  translateSortOptions(sortOptions: SortOption[]): SortOption[] {
    return sortOptions.map(option => ({
      ...option,
      label: this.translate.instant(option.label),
    }));
  }

  translateGraphTypes(graphTypes: any[]): any[] {
    console.log(graphTypes);
    return graphTypes.map(type => ({
      ...type,
      viewValue: this.translate.instant(type.viewValue),
    })); 
  }
  
}
