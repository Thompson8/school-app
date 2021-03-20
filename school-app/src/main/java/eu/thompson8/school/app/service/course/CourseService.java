package eu.thompson8.school.app.service.course;

import java.util.List;
import java.util.Optional;

import eu.thompson8.school.app.model.dto.course.CourseDto;
import eu.thompson8.school.app.model.dto.course.CoursePostPayloadDto;
import eu.thompson8.school.app.model.dto.course.CoursePutPayloadDto;
import eu.thompson8.school.app.model.dto.course.list.CourseListDto;

public interface CourseService {

	public Optional<CourseDto> getCourseById(Long id);

	public CourseListDto getCourses(String filter, int page, int size, List<String> sorting);

	public CourseDto createCourse(CoursePostPayloadDto payload);

	public CourseDto updateCourse(Long id, CoursePutPayloadDto payload);

	public void deleteCourse(Long id);

}
