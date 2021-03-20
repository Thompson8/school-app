import { NgModule } from '@angular/core';
import { BrowserModule, Title } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MenuComponent } from './component/menu/menu.component';
import { HomeComponent } from './component/home/home.component';
import { StudentListComponent } from './component/student-list/student-list.component';
import { CourseListComponent } from './component/course-list/course-list.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NbThemeModule, NbLayoutModule, NbMenuModule, NbSidebarModule, NbTreeGridModule, NbCardModule, NbSpinnerModule, NbSelectModule, NbIconModule, NbButtonModule, NbTooltipModule, NbAutocompleteModule, NbInputModule, NbBadgeModule, NbDialogModule, NbUserModule, NbCalendarModule, NbCalendarRangeModule, NbDatepickerModule } from '@nebular/theme';
import { NbEvaIconsModule } from '@nebular/eva-icons';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { StudentDialogComponent } from './component/student-dialog/student-dialog.component';
import { DeleteDialogComponent } from './component/delete-dialog/delete-dialog.component';
import { LoginComponent } from './component/login/login.component';
import { HeaderComponent } from './component/header/header.component';
import { CourseDialogComponent } from './component/course-dialog/course-dialog.component';

@NgModule({
  declarations: [
    AppComponent,
    MenuComponent,
    HomeComponent,
    StudentListComponent,
    CourseListComponent,
    StudentDialogComponent,
    DeleteDialogComponent,
    LoginComponent,
    HeaderComponent,
    CourseDialogComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    NbThemeModule.forRoot(),
    NbLayoutModule,
    NbEvaIconsModule,
    NbSidebarModule.forRoot(),
    NbMenuModule.forRoot(),
    NbTreeGridModule,
    NbCardModule,
    NbSpinnerModule,
    NbSelectModule,
    NbIconModule,
    NbButtonModule,
    NbTooltipModule,
    NbAutocompleteModule,
    NbInputModule,
    NbBadgeModule,
    NbDialogModule.forRoot(),
    NbUserModule,
    NbCalendarModule,
    NbCalendarRangeModule,
    NbDatepickerModule.forRoot()
  ],
  providers: [
    Title
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
