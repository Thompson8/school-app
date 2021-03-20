package eu.thompson8.school.app.mapper.student;

import java.util.HashSet;

import org.springframework.stereotype.Component;

import eu.thompson8.school.app.model.dto.student.StudentPostPayloadDto;
import eu.thompson8.school.app.model.dto.student.StudentDto;
import eu.thompson8.school.app.model.dto.student.StudentPutPayloadDto;
import eu.thompson8.school.app.model.dto.student.course.StudentCourseDto;
import eu.thompson8.school.app.model.dto.student.list.StudentListItemDto;
import eu.thompson8.school.app.model.entity.course.CourseEntity;
import eu.thompson8.school.app.model.entity.student.StudentEntity;
import eu.thompson8.school.app.util.mapper.MapperUtil;

@Component
public class StudentMapperImpl implements StudentMapper {

	@Override
	public StudentDto map(StudentEntity entity) {
		StudentDto dto = new StudentDto();
		copy(entity, dto);

		return dto;
	}

	@Override
	public StudentEntity map(StudentPostPayloadDto payload) {
		StudentEntity entity = new StudentEntity();
		initEntity(entity);
		copy(payload, entity);

		return entity;

	}

	@Override
	public void map(StudentPutPayloadDto from, StudentEntity to) {
		copy(from, to);
	}

	@Override
	public StudentListItemDto mapToListItem(StudentEntity entity) {
		StudentListItemDto item = new StudentListItemDto();
		copy(entity, item);

		return item;
	}

	private StudentCourseDto map(CourseEntity entity) {
		StudentCourseDto dto = new StudentCourseDto();
		dto.setId(entity.getId());
		dto.setName(entity.getName());

		return dto;
	}

	private void copy(StudentEntity from, StudentDto to) {
		to.setId(from.getId());
		to.setFirstName(from.getFirstName());
		to.setLastName(from.getLastName());
		to.setBirthDate(from.getBirthDate());

		to.setCourses(MapperUtil.mapSetToList(from.getCourses(), this::map));
	}

	public void copy(StudentPostPayloadDto from, StudentEntity to) {
		to.setFirstName(from.getFirstName());
		to.setLastName(from.getLastName());
		to.setBirthDate(from.getBirthDate());
	}

	public void copy(StudentPutPayloadDto from, StudentEntity to) {
		to.setFirstName(from.getFirstName());
		to.setLastName(from.getLastName());
		to.setBirthDate(from.getBirthDate());
	}

	public void copy(StudentEntity from, StudentListItemDto to) {
		to.setId(from.getId());
		to.setFirstName(from.getFirstName());
		to.setLastName(from.getLastName());
		to.setBirthDate(from.getBirthDate());
	}

	private void initEntity(StudentEntity entity) {
		entity.setCourses(new HashSet<>());
	}

}
