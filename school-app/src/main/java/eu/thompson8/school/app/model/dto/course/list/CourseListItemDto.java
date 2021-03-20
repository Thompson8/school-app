package eu.thompson8.school.app.model.dto.course.list;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CourseListItemDto {

	private Long id;

	private String code;
	
	private String name;

}
