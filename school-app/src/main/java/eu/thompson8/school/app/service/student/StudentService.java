package eu.thompson8.school.app.service.student;

import java.util.List;
import java.util.Optional;

import eu.thompson8.school.app.model.dto.student.StudentPostPayloadDto;
import eu.thompson8.school.app.model.dto.student.StudentDto;
import eu.thompson8.school.app.model.dto.student.StudentPutPayloadDto;
import eu.thompson8.school.app.model.dto.student.list.StudentListDto;

public interface StudentService {

	public Optional<StudentDto> getStudentById(Long id);

	public StudentListDto getStudents(String filter, int page, int size, List<String> sorting);

	public StudentDto createStudent(StudentPostPayloadDto payload);

	public StudentDto updateStudent(Long id, StudentPutPayloadDto payload);

	public void deleteStudent(Long id);

}
