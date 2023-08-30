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
    // Detecting default browser language to set website one
    const browserLang = translate.getBrowserLang() ?? 'en';
    translate.use(browserLang.match(/en|lt/) ? browserLang : 'en');
  }
}
