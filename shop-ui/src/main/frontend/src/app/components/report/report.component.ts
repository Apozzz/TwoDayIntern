import { DatePipe } from '@angular/common';
import { Component, OnDestroy } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup } from '@angular/forms';
import { ReportService } from '@services/report.service';
import { ToastrService } from 'ngx-toastr';
import { Subscription } from 'rxjs';


@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.less']
})
export class ReportComponent implements OnDestroy {

  selectedDate: Date | null = null;
  reportForm: FormGroup = this.initForm();
  private subscription: Subscription = new Subscription();

  constructor(
    private reportService: ReportService,
    private fb: FormBuilder,
    private toastr: ToastrService,
    private datePipe: DatePipe
  ) { }

  get dateTimeControl(): AbstractControl | null {
    return this.reportForm.get('dateTime');
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  downloadReport() {
    const selectedDateTime = this.dateTimeControl?.value;

    if (!selectedDateTime) {
      this.toastr.error('Please select a date first!', 'Error');
      return;
    }

    const formattedDate = this.datePipe.transform(selectedDateTime, 'yyyyMMdd-HHmm');

    if (!formattedDate) {
        this.toastr.error('Failed to format the date.', 'Error');
        return;
    }

    const reportSubscription = this.reportService.downloadCsvReport(formattedDate).subscribe({
      next: response => {
        this.promptUserToDownloadFile(response);
        this.toastr.success('File downloaded successfully!', 'Success');
      },
      error: error => {
        const errorMessage = error?.message || 'An error occurred while downloading the report. Please try again.';
        this.toastr.error(errorMessage, 'Error');
      },
    });

    this.subscription.add(reportSubscription);
  }

  onDateSelected(event: any) {
    this.selectedDate = event.value;
  }

  promptUserToDownloadFile(data: Blob): void {
    const blob = new Blob([data], { type: 'text/csv' });
    const url = window.URL.createObjectURL(blob);
    const link = document.createElement('a');
    const now = new Date();
    link.href = url;
    link.download = `report-${this.datePipe.transform(now, 'yyyyMMdd-HHmm')}.csv`;
    link.click();
    link.remove();
    window.URL.revokeObjectURL(url);
  }

  private padNumber(num: number): string {
    return num < 10 ? `0${num}` : `${num}`;
  }

  private initForm(): FormGroup {
    return this.fb.group({
      dateTime: [null]
    });
  }

}
