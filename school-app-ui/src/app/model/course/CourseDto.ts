import { CourseStudentDto } from "./CourseStudentDto";

export interface CourseDto {
    id: number,
    code: string,
    name: string,
    description?: string,
    students?: CourseStudentDto[]
}