export interface CourseListDto {
    number: number,
    totalPages: number,
    totalElements: number,
    items: CourseListItemDto[]
}

export interface CourseListItemDto {
    id: number,
    code: string,
    name: string
}