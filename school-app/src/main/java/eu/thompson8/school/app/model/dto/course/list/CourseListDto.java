package eu.thompson8.school.app.model.dto.course.list;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CourseListDto {

	private Integer number;

	private Integer totalPages;

	private Long totalElements;

	private List<CourseListItemDto> items;

}
