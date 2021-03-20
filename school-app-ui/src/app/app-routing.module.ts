import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CourseListComponent } from './component/course-list/course-list.component';
import { HomeComponent } from './component/home/home.component';
import { LoginComponent } from './component/login/login.component';
import { StudentListComponent } from './component/student-list/student-list.component';

const routes: Routes = [{ path: "", component: HomeComponent, data: { title: 'School App' } },
{ path: "home", component: HomeComponent, data: { title: 'School App' } },
{ path: "login", component: LoginComponent, data: { title: 'Students' } },
{ path: "course", component: CourseListComponent, data: { title: 'Courses' } },
{ path: "student", component: StudentListComponent, data: { title: 'Students' } }];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
