import { Component, Output } from '@angular/core';
import { Subject } from 'rxjs';
import { debounceTime } from 'rxjs/operators';

@Component({
  selector: 'app-product-sort',
  templateUrl: './product-sort.component.html',
  styleUrls: ['./product-sort.component.less']
})
export class ProductSortComponent {

  private sortChangedSubject = new Subject<string>();
  @Output() sortChanged = this.sortChangedSubject.pipe(debounceTime(1000));
  sortOption: string = 'id-asc';

  onSortChange() {
    this.sortChangedSubject.next(
      this.sortOption,
    );
  }

}
