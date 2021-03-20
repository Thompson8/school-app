package eu.thompson8.school.app.mapper.course;

import eu.thompson8.school.app.model.dto.course.CourseDto;
import eu.thompson8.school.app.model.dto.course.CoursePostPayloadDto;
import eu.thompson8.school.app.model.dto.course.CoursePutPayloadDto;
import eu.thompson8.school.app.model.dto.course.list.CourseListItemDto;
import eu.thompson8.school.app.model.entity.course.CourseEntity;

public interface CourseMapper {

	public CourseDto map(CourseEntity entity);

	public CourseEntity map(CoursePostPayloadDto payload);

	public void map(CoursePutPayloadDto from, CourseEntity to);

	public CourseListItemDto mapToListItem(CourseEntity entity);

}
