import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NbDialogRef } from '@nebular/theme';
import { StudentPostPayloadDto } from 'src/app/model/student/StudentPostPayloadDto';
import { StudentPutPayloadDto } from 'src/app/model/student/StudentPutPayloadDto';
import { StudentService } from 'src/app/student.service';

@Component({
  selector: 'app-student-dialog',
  templateUrl: './student-dialog.component.html',
  styleUrls: ['./student-dialog.component.scss']
})
export class StudentDialogComponent implements OnInit {

  @Input() mode: string | undefined;

  @Input() studentId: number | undefined;

  studentForm: FormGroup;

  loading: boolean;

  submitted: boolean;

  success: boolean;

  error: boolean;

  constructor(private studentService: StudentService, private formBuilder: FormBuilder, private dialogRef: NbDialogRef<any>) {
    this.studentForm = this.formBuilder.group({
      firstName: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(64)]],
      lastName: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(64)]],
      birthDate: [null, [Validators.required]]
    });
    this.loading = false;
    this.success = false;
    this.error = false;
    this.submitted = false;
  }

  ngOnInit(): void {
    if (this.isView()) {
      this.loadStudent();
      this.disableFields();
    } else if (this.isModify()) {
      this.loadStudent();
    }
  }

  get studentFormControl() {
    return this.studentForm.controls;
  }

  loadStudent() {
    this.startLoading();
    this.studentService.getStudent(this.studentId!).subscribe(e => {
      this.studentForm.get('firstName')?.setValue(e.firstName);
      this.studentForm.get('lastName')?.setValue(e.lastName);
      this.studentForm.get('birthDate')?.setValue(new Date(e.birthDate));
      this.stopLoading();
    }, error => this.handleError(error));
  }

  createStudent() {
    this.submitted = true;
    this.success = false;
    if (this.isValid()) {
      let payload = {
        firstName: this.studentForm.get('firstName')?.value,
        lastName: this.studentForm.get('lastName')?.value,
        birthDate: this.formatData(this.studentForm.get('birthDate')?.value)
      };
      this.create(payload);
    }
  }

  updateStudent() {
    this.submitted = true;
    this.success = false;
    if (this.isValid()) {
      let payload = {
        firstName: this.studentForm.get('firstName')?.value,
        lastName: this.studentForm.get('lastName')?.value,
        birthDate: this.formatData(this.studentForm.get('birthDate')?.value)
      };
      this.update(payload);
    }
  }

  create(payload: StudentPostPayloadDto) {
    this.startLoading();
    this.studentService.createStudent(payload).subscribe(e => this.close(), error => this.handleError(error));
  }

  update(payload: StudentPutPayloadDto) {
    this.startLoading();
    this.success = false;
    this.studentService.updateStudent(this.studentId!, payload).subscribe(e => {
      this.stopLoading();
      this.success = true;
    }, error => this.handleError(error));
  }

  formatData(date: any) {
    return new Date(date.getTime() - date.getTimezoneOffset() * 60 * 1000).toISOString().split('T')[0];
  }

  startEdit() {
    this.mode = 'MODIFY';
    this.enableFields();
  }

  startLoading() {
    this.loading = true;
  }

  stopLoading() {
    this.loading = false;
  }

  handleError(error: any) {
    console.log('Error occured ' + error);
    this.error = true;
    this.stopLoading();
  }

  disableFields() {
    this.studentForm.get('firstName')?.disable();
    this.studentForm.get('lastName')?.disable();
    this.studentForm.get('birthDate')?.disable();
  }

  enableFields() {
    this.studentForm.get('firstName')?.enable();
    this.studentForm.get('lastName')?.enable();
    this.studentForm.get('birthDate')?.enable();
  }

  isValid() {
    return !this.studentForm.invalid;
  }

  isCreate() {
    return 'CREATE' === this.mode;
  }

  isModify() {
    return 'MODIFY' === this.mode;
  }

  isView() {
    return 'VIEW' === this.mode;
  }

  close() {
    this.dialogRef.close();
  }

}
