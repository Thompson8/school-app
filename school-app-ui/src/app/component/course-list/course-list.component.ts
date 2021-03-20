import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { NbDialogService, NbSortDirection, NbSortRequest, NbTreeGridDataSource, NbTreeGridDataSourceBuilder } from '@nebular/theme';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { CourseService } from 'src/app/course.service';
import { CourseListDto, CourseListItemDto } from 'src/app/model/course/CourseListItemDto';
import { CourseDialogComponent } from '../course-dialog/course-dialog.component';
import { DeleteDialogComponent } from '../delete-dialog/delete-dialog.component';

@Component({
  selector: 'app-course-list',
  templateUrl: './course-list.component.html',
  styleUrls: ['./course-list.component.scss']
})
export class CourseListComponent implements OnInit {

  columns = [{ id: '1', name: 'Code', property: 'code', isAction: null },
  { id: '2', name: 'Name', property: 'name', isAction: null },
  { id: '3', name: null, property: '', isAction: true }];

  dataSource: NbTreeGridDataSource<CourseListItemDto>;

  pageNumber: number;

  pageSizes: number[];

  pageSize: number;

  totalPages: number;

  totalElements: number;

  sortColumn: string | null;

  sortDirection: NbSortDirection;

  loading: boolean;

  columnIds: string[];

  searchFormControl: FormControl;

  searchOptions: string[];

  filteredOptions: Observable<string[]>;

  searchValue: string | null;

  filterValue: string | null;

  selectedSearchOptions: any[];

  constructor(private courseService: CourseService, private dataSourceBuilder: NbTreeGridDataSourceBuilder<CourseListItemDto>, private dialogService: NbDialogService) {
    this.dataSource = dataSourceBuilder.create([]);
    this.pageNumber = 0;
    this.pageSizes = [5, 10, 25, 50, 100];
    this.pageSize = 5;
    this.totalElements = 0;
    this.totalPages = 0;
    this.sortColumn = null;
    this.sortDirection = NbSortDirection.NONE;
    this.loading = false;

    this.columnIds = this.columns.map(e => e.id);

    this.searchFormControl = new FormControl();
    this.searchOptions = this.columns.filter(e => e.name !== null).map(e => e.name!);
    this.filteredOptions = this.searchFormControl.valueChanges.pipe(startWith(''), map(filterString => this.filter(filterString)));
    this.searchValue = null;
    this.filterValue = null;
    this.selectedSearchOptions = [];
  }

  ngOnInit(): void {
    this.loadData();
  }

  refresh() {
    this.loadData();
  }

  loadData() {
    var sorting = this.sortColumn !== null ? this.columns.filter(e => e.id === this.sortColumn)[0].property : null;
    this.loading = true;
    this.courseService.getCourses(this.filterValue, this.pageNumber, this.pageSize, sorting, this.sortDirection).subscribe(e => this.updateData(e));
  }

  updateData(list: CourseListDto) {
    this.dataSource.setData(list.items.map(e => ({ data: e })));
    this.totalPages = list.totalPages;
    this.totalElements = list.totalElements;
    this.loading = false;
  }

  updateSort(sortRequest: NbSortRequest): void {
    this.sortColumn = sortRequest.column;
    this.sortDirection = sortRequest.direction;
    this.loadData();
  }

  private filter(value: string): string[] {
    if (value !== null) {
      const filterValue = value.toLowerCase();
      return this.searchOptions.filter(optionValue => optionValue.toLowerCase().includes(filterValue));
    } else {
      return this.searchOptions;
    }
  }

  search() {
    if (this.searchFormControl.value !== null) {
      let properties = this.columns.filter(e => e.name === this.searchFormControl.value).map(e => e.property);
      if (properties.length == 1) {
        let selectedSearchFormValue = properties[0];
        let selectedSearchValue = this.searchValue;
        this.filterValue = selectedSearchFormValue !== null && selectedSearchValue !== null ? selectedSearchFormValue + '==\'' + selectedSearchValue + '%\'' : null;
        this.loadData();
      }
    }
  }

  clearSearch() {
    this.searchFormControl.setValue('');
    this.searchValue = null;
  }

  getSortDirection(column: string): NbSortDirection {
    if (this.sortColumn === column) {
      return this.sortDirection;
    }

    return NbSortDirection.NONE;
  }

  calculateElementsOnPageStart() {
    return this.pageNumber * this.pageSize + 1;
  }

  calculateElementsOnPageEnd() {
    return Math.min((this.pageNumber + 1) * this.pageSize, this.totalElements);
  }

  changePageSize() {
    this.pageNumber = 0;
    this.loadData();
  }

  prevPage() {
    this.pageNumber -= 1;
    this.loadData();
  }

  nextPage() {
    this.pageNumber += 1;
    this.loadData();
  }

  openCreateDialog() {
    let dialogRef = this.dialogService.open(CourseDialogComponent, { context: { mode: 'CREATE' } });
    dialogRef.onClose.subscribe(e => this.refresh());
  }

  openViewDialog(id: number) {
    let dialogRef = this.dialogService.open(CourseDialogComponent, { context: { courseId: id, mode: 'VIEW' } });
    dialogRef.onClose.subscribe(e => this.refresh());
  }

  openEditDialog(id: number) {
    let dialogRef = this.dialogService.open(CourseDialogComponent, { context: { courseId: id, mode: 'MODIFY' } });
    dialogRef.onClose.subscribe(e => this.refresh());
  }

  openDeleteDialog(id: number) {
    let dialogRef = this.dialogService.open(DeleteDialogComponent, { context: { action: this.courseService.deleteCourse(id) } });
    dialogRef.onClose.subscribe(e => this.refresh());
  }


}
