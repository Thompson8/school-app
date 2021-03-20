export interface CoursePutPayloadDto {
    name: string,
    code: string,
    description?: string,
    students?: number[]
}