import { Injectable } from '@angular/core';
import { MONTH_NAMES } from '@constants/month-names.constants';
import { TranslateService } from '@ngx-translate/core';

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
      label: this.translate.instant(attr.label)
    }));
  }
  
}
