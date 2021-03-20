package eu.thompson8.school.app.service.student;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eu.thompson8.school.app.exception.entity.EntityNotFoundException;
import eu.thompson8.school.app.mapper.student.StudentMapper;
import eu.thompson8.school.app.model.dto.student.StudentPostPayloadDto;
import eu.thompson8.school.app.model.dto.student.StudentDto;
import eu.thompson8.school.app.model.dto.student.StudentPutPayloadDto;
import eu.thompson8.school.app.model.dto.student.list.StudentListDto;
import eu.thompson8.school.app.model.entity.student.StudentEntity;
import eu.thompson8.school.app.repository.student.StudentRepository;
import eu.thompson8.school.app.util.rsql.jpa.JpaRsqlParser;
import eu.thompson8.school.app.util.sorting.SortingParser;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

	private final StudentRepository repository;

	private final StudentMapper mapper;

	@Override
	@Transactional(readOnly = true)
	public Optional<StudentDto> getStudentById(Long id) {
		return repository.findById(id).map(mapper::map);
	}

	@Override
	@Transactional(readOnly = true)
	public StudentListDto getStudents(String filter, int page, int size, List<String> sorting) {
		Page<StudentEntity> queryResult = repository.findAll(JpaRsqlParser.parse(filter),
				PageRequest.of(page, size, SortingParser.parse(sorting)));

		StudentListDto result = new StudentListDto();
		result.setNumber(queryResult.getNumber());
		result.setTotalElements(queryResult.getTotalElements());
		result.setTotalPages(queryResult.getTotalPages());
		result.setItems(queryResult.stream().map(mapper::mapToListItem).collect(Collectors.toList()));

		return result;

	}

	@Override
	@Transactional
	public StudentDto createStudent(StudentPostPayloadDto payload) {
		StudentEntity entity = mapper.map(payload);
		return mapper.map(repository.save(entity));
	}

	@Override
	@Transactional
	public StudentDto updateStudent(Long id, StudentPutPayloadDto payload) {
		StudentEntity entity = repository.findById(id).orElseThrow(EntityNotFoundException::new);
		mapper.map(payload, entity);

		return mapper.map(entity);
	}

	@Override
	@Transactional
	public void deleteStudent(Long id) {
		if (repository.existsById(id)) {
			repository.deleteById(id);
		}
	}

}
