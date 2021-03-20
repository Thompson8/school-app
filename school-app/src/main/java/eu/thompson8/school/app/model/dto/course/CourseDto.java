package eu.thompson8.school.app.model.dto.course;

import java.util.List;

import eu.thompson8.school.app.model.dto.course.student.CourseStudentDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CourseDto {

	private Long id;
	
	private String code;

	private String name;

	private String description;

	private List<CourseStudentDto> students;

}
