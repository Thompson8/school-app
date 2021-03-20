export interface CoursePostPayloadDto {
    name: string,
    code: string,
    description?: string,
    students?: number[]
}