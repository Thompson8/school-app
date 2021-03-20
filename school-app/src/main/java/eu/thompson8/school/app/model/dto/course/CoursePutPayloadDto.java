package eu.thompson8.school.app.model.dto.course;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CoursePutPayloadDto {
	
	@NotNull
	@Size(min = 1, max = 128)
	private String code;
	
	@NotNull
	@Size(min = 1, max = 128)
	private String name;

	private String description;

	private List<Long> students;
	
}
