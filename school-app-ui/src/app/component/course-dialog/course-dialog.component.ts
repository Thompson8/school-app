import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { NbDialogRef } from '@nebular/theme';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { CourseService } from 'src/app/course.service';
import { CoursePostPayloadDto } from 'src/app/model/course/CoursePostPayloadDto';
import { CoursePutPayloadDto } from 'src/app/model/course/CoursePutPayloadDto';
import { CourseStudentDto } from 'src/app/model/course/CourseStudentDto';
import { StudentListDto, StudentListItemDto } from 'src/app/model/student/StudentListItemDto';
import { StudentService } from 'src/app/student.service';

@Component({
  selector: 'app-course-dialog',
  templateUrl: './course-dialog.component.html',
  styleUrls: ['./course-dialog.component.scss']
})
export class CourseDialogComponent implements OnInit {

  @Input() mode: string | undefined;

  @Input() courseId: number | undefined;

  courseForm: FormGroup;

  loading: boolean;

  submitted: boolean;

  success: boolean;

  error: boolean;

  courseStudents: CourseStudentDto[];

  studentSearchFormControl: FormControl;

  studentSearchFilteredOptions: StudentListItemDto[];

  constructor(private courseService: CourseService, private studentService: StudentService, private formBuilder: FormBuilder, private dialogRef: NbDialogRef<any>) {
    this.courseForm = this.formBuilder.group({
      code: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(128)]],
      name: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(128)]],
      description: [null, null]
    });
    this.loading = false;
    this.success = false;
    this.error = false;
    this.submitted = false;
    this.courseStudents = [];
    this.studentSearchFormControl = new FormControl();
    this.studentSearchFilteredOptions = [];
    this.studentSearchFormControl.valueChanges.pipe(startWith(''), map(e => this.getStudens(e).pipe(map(e => e.items)))).subscribe(e => e.subscribe(a => this.studentSearchFilteredOptions = a));
  }

  ngOnInit(): void {
    if (this.isView()) {
      this.loadCourse();
      this.disableFields();
    } else if (this.isModify()) {
      this.loadCourse();
    }
  }

  get courseFormControl() {
    return this.courseForm.controls;
  }

  loadCourse() {
    this.startLoading();
    this.courseService.getCourse(this.courseId!).subscribe(e => {
      this.courseForm.get('code')?.setValue(e.code);
      this.courseForm.get('name')?.setValue(e.name);
      this.courseForm.get('description')?.setValue(e.description);
      if (e.students != null) {
        this.courseStudents = e.students;
      }
      this.stopLoading();
    }, error => this.handleError(error));
  }

  getStudens(name: any) {
    let filter = null;
    if (name instanceof String) {
      let parts = name.split(' ');
      filter = 'firstName==\'' + parts[0] + '%\'';
      if (parts.length > 1) {
        filter += ';lastName==\'' +
          parts.splice(1).reduce((a, b) => a + ' ' + b) + '%\'';
      }
    }
    return this.studentService.getStudents(filter, 0, 10, 'firstName', 'asc');
  }

  createCourse() {
    this.submitted = true;
    this.success = false;
    if (this.isValid()) {
      let payload = {
        code: this.courseForm.get('code')?.value,
        name: this.courseForm.get('name')?.value,
        description: this.courseForm.get('description')?.value,
        students: this.courseStudents.map(e => e.id)
      };
      this.create(payload);
    }
  }

  updateCourse() {
    this.submitted = true;
    this.success = false;
    if (this.isValid()) {
      let payload = {
        code: this.courseForm.get('code')?.value,
        name: this.courseForm.get('name')?.value,
        description: this.courseForm.get('description')?.value,
        students: this.courseStudents.map(e => e.id)
      };
      this.update(payload);
    }
  }

  create(payload: CoursePostPayloadDto) {
    this.startLoading();
    this.courseService.createCourse(payload).subscribe(e => this.close(), error => this.handleError(error));
  }

  update(payload: CoursePutPayloadDto) {
    this.startLoading();
    this.success = false;
    this.courseService.updateCourse(this.courseId!, payload).subscribe(e => {
      this.stopLoading();
      this.success = true;
    }, error => this.handleError(error));
  }

  removeStudent(index: number) {
    this.courseStudents.splice(index, 1);
  }

  addStudent() {
    let selected = this.studentSearchFormControl.value;
    if (selected != null && selected.id != null) {
      this.courseStudents.push(selected);
      this.studentSearchFormControl.setValue('');
    }
  }

  viewHandle(value: any) {
    if (value.firstName != null && value.lastName != null) {
      return value.firstName + ' ' + value.lastName;
    } else {
      return value;
    }
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
    this.courseForm.get('code')?.disable();
    this.courseForm.get('name')?.disable();
    this.courseForm.get('description')?.disable();
  }

  enableFields() {
    this.courseForm.get('code')?.enable();
    this.courseForm.get('name')?.enable();
    this.courseForm.get('description')?.enable();
  }

  isValid() {
    return !this.courseForm.invalid;
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
