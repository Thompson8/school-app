import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { CourseDto } from './model/course/CourseDto';
import { CourseListDto } from './model/course/CourseListItemDto';
import { CoursePostPayloadDto } from './model/course/CoursePostPayloadDto';
import { CoursePutPayloadDto } from './model/course/CoursePutPayloadDto';
import { UserService } from './user.service';

@Injectable({
  providedIn: 'root'
})
export class CourseService {

  private url: string = environment.apiUrl + "/course";

  constructor(private http: HttpClient, private userService: UserService) { }

  getCourses(filter: string | null, page: number, pageSize: number, sortByField: string | null, sortDirection: string | null): Observable<CourseListDto> {
    let params = new HttpParams().set("page", page.toString()).set("size", pageSize.toString());
    if (filter !== null) {
      params = params.set('filter', filter);
    }
    if (sortByField !== null && sortDirection !== null && sortDirection !== '') {
      params = params.set('sorting', sortByField + ':' + sortDirection);
    }

    let headers = this.getHttpHeaders();
    return this.http.get<CourseListDto>(this.url, { headers: headers, params: params });
  }

  getCourse(id: number) {
    let headers = this.getHttpHeaders();
    return this.http.get<CourseDto>(this.url + '/' + id, { headers: headers });
  }

  createCourse(payload: CoursePostPayloadDto) {
    let headers = this.getHttpHeaders();
    return this.http.post<CoursePostPayloadDto>(this.url, payload, { headers: headers });
  }

  updateCourse(id: number, payload: CoursePutPayloadDto) {
    let headers = this.getHttpHeaders();
    return this.http.put<CoursePutPayloadDto>(this.url + '/' + id, payload, { headers: headers });
  }

  deleteCourse(id: number) {
    let headers = this.getHttpHeaders();
    return this.http.delete(this.url + '/' + id, { headers: headers });
  }

  private getHttpHeaders(): HttpHeaders {
    return new HttpHeaders()
      .set('Content-Type', 'application/json')
      .set("Authorization", "Basic " + this.userService.getBasic());
  }

}
