package eu.thompson8.school.app.mapper.course;

import java.util.HashSet;

import org.springframework.stereotype.Component;

import eu.thompson8.school.app.model.dto.course.CourseDto;
import eu.thompson8.school.app.model.dto.course.CoursePostPayloadDto;
import eu.thompson8.school.app.model.dto.course.CoursePutPayloadDto;
import eu.thompson8.school.app.model.dto.course.list.CourseListItemDto;
import eu.thompson8.school.app.model.dto.course.student.CourseStudentDto;
import eu.thompson8.school.app.model.entity.course.CourseEntity;
import eu.thompson8.school.app.model.entity.student.StudentEntity;
import eu.thompson8.school.app.repository.student.StudentRepository;
import eu.thompson8.school.app.util.mapper.MapperUtil;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CourseMapperImpl implements CourseMapper {

	private final StudentRepository studentRepository;

	@Override
	public CourseDto map(CourseEntity entity) {
		CourseDto dto = new CourseDto();
		copy(entity, dto);

		return dto;
	}

	@Override
	public CourseEntity map(CoursePostPayloadDto payload) {
		CourseEntity entity = new CourseEntity();
		initEntity(entity);
		copy(payload, entity);

		return entity;
	}

	@Override
	public void map(CoursePutPayloadDto from, CourseEntity to) {
		copy(from, to);
	}

	@Override
	public CourseListItemDto mapToListItem(CourseEntity entity) {
		CourseListItemDto item = new CourseListItemDto();
		copy(entity, item);

		return item;
	}

	private CourseStudentDto map(StudentEntity entity) {
		CourseStudentDto dto = new CourseStudentDto();
		dto.setId(entity.getId());
		dto.setFirstName(entity.getFirstName());
		dto.setLastName(entity.getLastName());
		dto.setBirhtDate(entity.getBirthDate());

		return dto;
	}

	private void copy(CourseEntity from, CourseDto to) {
		to.setId(from.getId());
		to.setCode(from.getCode());
		to.setName(from.getName());
		to.setDescription(from.getDescription());
		to.setStudents(MapperUtil.mapSetToList(from.getStudents(), this::map));
	}

	public void copy(CoursePostPayloadDto from, CourseEntity to) {
		to.setCode(from.getCode());
		to.setName(from.getName());
		to.setDescription(from.getDescription());
		MapperUtil.mapEntityList(from.getStudents(), to.getStudents(), studentRepository);
	}

	public void copy(CoursePutPayloadDto from, CourseEntity to) {
		to.setCode(from.getCode());
		to.setName(from.getName());
		to.setDescription(from.getDescription());
		MapperUtil.mapEntityList(from.getStudents(), to.getStudents(), studentRepository);
	}

	private void copy(CourseEntity from, CourseListItemDto to) {
		to.setId(from.getId());
		to.setCode(from.getCode());
		to.setName(from.getName());
	}

	private void initEntity(CourseEntity entity) {
		entity.setStudents(new HashSet<>());
	}

}
