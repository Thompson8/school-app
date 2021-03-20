package eu.thompson8.school.app.mapper.student;

import eu.thompson8.school.app.model.dto.student.StudentDto;
import eu.thompson8.school.app.model.dto.student.StudentPostPayloadDto;
import eu.thompson8.school.app.model.dto.student.StudentPutPayloadDto;
import eu.thompson8.school.app.model.dto.student.list.StudentListItemDto;
import eu.thompson8.school.app.model.entity.student.StudentEntity;

public interface StudentMapper {

	public StudentDto map(StudentEntity entity);

	public StudentEntity map(StudentPostPayloadDto payload);

	public void map(StudentPutPayloadDto from, StudentEntity to);

	public StudentListItemDto mapToListItem(StudentEntity entity);

}
