import { Component } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.less']
})
export class AppComponent {
  title = 'your-angular-app';
  constructor(private translate: TranslateService) {
    // Detecting browser language
    const browserLang = translate.getBrowserLang() ?? 'en';
    translate.use(browserLang.match(/en|lt/) ? browserLang : 'en');
  }
}
