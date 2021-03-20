import { Component, Input, OnInit } from '@angular/core';
import { NbDialogRef } from '@nebular/theme';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-delete-dialog',
  templateUrl: './delete-dialog.component.html',
  styleUrls: ['./delete-dialog.component.scss']
})
export class DeleteDialogComponent implements OnInit {

  @Input() action: Observable<Object> | undefined;

  loading: boolean;

  deleteError: boolean;

  constructor(private dialogRef: NbDialogRef<any>) {
    this.loading = false;
    this.deleteError = false;
  }

  ngOnInit(): void {
  }

  delete() {
    this.deleteError = false;
    this.loading = true;
    this.action!.subscribe(e => this.close(), error => this.handleError(error));
  }

  handleError(error: any) {
    this.deleteError = true;
    this.loading = false;
  }

  close() {
    this.dialogRef.close();
  }

}
