import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MAT_DATE_FORMATS } from '@angular/material/core';
import { MatDatepicker } from '@angular/material/datepicker';
import { DATE_YEAR_FORMAT } from '@constants/date-formats.constants';
import { LOCAL_STORAGE_KEYS } from '@constants/local-storage-keys.constants';
import * as moment from 'moment';
import { Moment } from 'moment';

@Component({
  selector: 'app-profit',
  templateUrl: './profit.component.html',
  styleUrls: ['./profit.component.less'],
  providers: [
    { provide: MAT_DATE_FORMATS, useValue: DATE_YEAR_FORMAT },
  ],
})
export class ProfitComponent implements OnInit {

  date = new FormControl(moment().startOf('year'));

  ngOnInit(): void {
    const savedYear = localStorage.getItem(LOCAL_STORAGE_KEYS.SELECTED_PURCHASE_GRAPH_YEAR);

    if (!savedYear) {
      return;
    }

    this.setDateYearValue(+savedYear);
  }

  chosenYearHandler(selectedDate: Moment, datepicker: MatDatepicker<Moment>) {
    const dateYear = selectedDate.year();
    this.setDateYearValue(dateYear);
    localStorage.setItem(LOCAL_STORAGE_KEYS.SELECTED_PURCHASE_GRAPH_YEAR, dateYear.toString());
    datepicker.close();
  }

  setDateYearValue(yearDate: number) {
    const ctrlValue = this.date.value!;
    ctrlValue.year(yearDate);
    this.date.setValue(ctrlValue);
  }

}
