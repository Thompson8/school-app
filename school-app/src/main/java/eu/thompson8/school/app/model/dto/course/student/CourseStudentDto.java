package eu.thompson8.school.app.model.dto.course.student;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CourseStudentDto {

	private Long id;

	private String firstName;

	private String lastName;

	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate birhtDate;

}
