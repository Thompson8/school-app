package eu.thompson8.school.app.service.course;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eu.thompson8.school.app.exception.entity.EntityNotFoundException;
import eu.thompson8.school.app.mapper.course.CourseMapper;
import eu.thompson8.school.app.model.dto.course.CourseDto;
import eu.thompson8.school.app.model.dto.course.CoursePostPayloadDto;
import eu.thompson8.school.app.model.dto.course.CoursePutPayloadDto;
import eu.thompson8.school.app.model.dto.course.list.CourseListDto;
import eu.thompson8.school.app.model.entity.course.CourseEntity;
import eu.thompson8.school.app.repository.course.CourseRepository;
import eu.thompson8.school.app.util.rsql.jpa.JpaRsqlParser;
import eu.thompson8.school.app.util.sorting.SortingParser;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {

	private final CourseRepository repository;

	private final CourseMapper mapper;

	@Override
	@Transactional(readOnly = true)
	public Optional<CourseDto> getCourseById(Long id) {
		return repository.findById(id).map(mapper::map);
	}

	@Override
	@Transactional(readOnly = true)
	public CourseListDto getCourses(String filter, int page, int size, List<String> sorting) {
		Page<CourseEntity> queryResult = repository.findAll(JpaRsqlParser.parse(filter),
				PageRequest.of(page, size, SortingParser.parse(sorting)));

		CourseListDto result = new CourseListDto();
		result.setNumber(queryResult.getNumber());
		result.setTotalElements(queryResult.getTotalElements());
		result.setTotalPages(queryResult.getTotalPages());
		result.setItems(queryResult.stream().map(mapper::mapToListItem).collect(Collectors.toList()));

		return result;
	}

	@Override
	@Transactional
	public CourseDto createCourse(CoursePostPayloadDto payload) {
		CourseEntity entity = mapper.map(payload);
		return mapper.map(repository.save(entity));
	}

	@Override
	@Transactional
	public CourseDto updateCourse(Long id, CoursePutPayloadDto payload) {
		CourseEntity entity = repository.findById(id).orElseThrow(EntityNotFoundException::new);
		mapper.map(payload, entity);

		return mapper.map(entity);
	}

	@Override
	@Transactional
	public void deleteCourse(Long id) {
		if (repository.existsById(id)) {
			repository.deleteById(id);
		}
	}

}
