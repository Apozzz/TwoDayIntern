import { Component, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { SUPPORTED_LANGUAGES } from '@constants/languages.constants';
import { LOCAL_STORAGE_KEYS } from '@constants/local-storage-keys.constants';
import { LocalStorageService } from '@services/local-storage.service';

@Component({
  selector: 'app-language',
  templateUrl: './language.component.html',
  styleUrls: ['./language.component.less']
})
export class LanguageComponent implements OnInit {

  languages = SUPPORTED_LANGUAGES;
  currentLanguage: string = this.translate.currentLang;

  constructor(private translate: TranslateService, private localStorageService: LocalStorageService) { }

  ngOnInit(): void {
    const savedLanguage = this.localStorageService.get<string>(LOCAL_STORAGE_KEYS.SELECTED_LANGUAGE);

    if (!savedLanguage) {
      return;
    }

    this.currentLanguage = savedLanguage;
    this.translate.use(savedLanguage);
  }

  changeLanguage(event: Event) {
    const selectElement = event.target as HTMLSelectElement;
    const langCode = selectElement.value;
    this.currentLanguage = langCode;
    this.translate.use(langCode);
    this.localStorageService.set(LOCAL_STORAGE_KEYS.SELECTED_LANGUAGE, langCode);
  }
}