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
    this.initializeLanguage();
  }

  private initializeLanguage(): void {
    const savedLanguage = this.localStorageService.get<string>(LOCAL_STORAGE_KEYS.SELECTED_LANGUAGE);

    if (this.isSupportedLanguage(savedLanguage)) {
      this.currentLanguage = savedLanguage!;
      this.translate.use(savedLanguage!);
    }
  }

  changeLanguage(): void {
    if (this.isSupportedLanguage(this.currentLanguage)) {
      this.translate.use(this.currentLanguage);
      this.localStorageService.set(LOCAL_STORAGE_KEYS.SELECTED_LANGUAGE, this.currentLanguage);
    }
  }

  private isSupportedLanguage(langCode: string | null): boolean {
    return !!langCode && this.languages.some(lang => lang.code === langCode);
  }

}