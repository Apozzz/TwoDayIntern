import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ReportService } from '@services/report.service';
import { formatDateForReport } from '@utils/date-util';

@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.less']
})
export class ReportComponent implements OnInit {

  selectedDate: Date | null = null;
  reportForm!: FormGroup;

  constructor(private reportService: ReportService, private fb: FormBuilder) {}

  ngOnInit() {
    this.reportForm = this.fb.group({
      dateTime: [null]
    });
  }

  downloadReport() {
    const selectedDateTime = this.reportForm.get('dateTime')?.value;
    
    if (!selectedDateTime) {
      alert('Please select a date first!');
      return;
    }

    const formattedDate = formatDateForReport(selectedDateTime);

    this.reportService.downloadCsvReport(formattedDate).subscribe({
      next: response => this.promptUserToDownloadFile(response),
      error: (error) => {
        console.error('Error downloading report:', error);
        alert('Error downloading report. Please try again later.');
      },
    });
  }

  promptUserToDownloadFile(data: Blob) {
    const blob = new Blob([data], { type: 'text/csv' });
    const url = window.URL.createObjectURL(blob);
    const link = document.createElement('a');
    link.href = url;
    link.download = 'report.csv';
    link.click();
    window.URL.revokeObjectURL(url);
  }

  onDateSelected(event: any) {
    this.selectedDate = event.value;
  }

}
