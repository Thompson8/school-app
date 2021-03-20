import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { StudentDto } from './model/student/StudentDto';
import { StudentListDto } from './model/student/StudentListItemDto';
import { StudentPostPayloadDto } from './model/student/StudentPostPayloadDto';
import { StudentPutPayloadDto } from './model/student/StudentPutPayloadDto';
import { UserService } from './user.service';

@Injectable({
  providedIn: 'root'
})
export class StudentService {

  private url: string =  environment.apiUrl + "/student";

  constructor(private http: HttpClient, private userService: UserService) { }

  getStudents(filter: string | null, page: number, pageSize: number, sortByField: string | null, sortDirection: string | null): Observable<StudentListDto> {
    let params = new HttpParams().set("page", page.toString()).set("size", pageSize.toString());
    if (filter !== null) {
      params = params.set('filter', filter);
    }
    if (sortByField !== null && sortDirection !== null && sortDirection !== '') {
      params = params.set('sorting', sortByField + ':' + sortDirection);
    }

    let headers = this.getHttpHeaders();
    return this.http.get<StudentListDto>(this.url, { headers: headers, params: params });
  }

  getStudent(id: number) {
    let headers = this.getHttpHeaders();
    return this.http.get<StudentDto>(this.url + '/' + id, { headers: headers });
  }

  createStudent(payload: StudentPostPayloadDto) {
    let headers = this.getHttpHeaders();
    return this.http.post<StudentPostPayloadDto>(this.url, payload, { headers: headers });
  }

  updateStudent(id: number, payload: StudentPutPayloadDto) {
    let headers = this.getHttpHeaders();
    return this.http.put<StudentPutPayloadDto>(this.url + '/' + id, payload, { headers: headers });
  }

  deleteStudent(id: number) {
    let headers = this.getHttpHeaders();
    return this.http.delete(this.url + '/' + id, { headers: headers });
  }

  private getHttpHeaders(): HttpHeaders {
    return new HttpHeaders()
      .set('Content-Type', 'application/json')
      .set("Authorization", "Basic " + this.userService.getBasic());
  }

}
