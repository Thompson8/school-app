export interface StudentListDto {
    number: number,
    totalPages: number,
    totalElements: number,
    items: StudentListItemDto[]
}

export interface StudentListItemDto {
    id: number,
    firstName?: string,
    lastName?: string
}