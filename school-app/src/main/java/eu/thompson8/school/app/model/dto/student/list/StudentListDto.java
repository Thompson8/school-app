package eu.thompson8.school.app.model.dto.student.list;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StudentListDto {

	private Integer number;
	
	private Integer totalPages;
	
	private Long totalElements;

	private List<StudentListItemDto> items;

}
