package eu.thompson8.school.app.controller.course;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import eu.thompson8.school.app.model.dto.course.CourseDto;
import eu.thompson8.school.app.model.dto.course.CoursePostPayloadDto;
import eu.thompson8.school.app.model.dto.course.CoursePutPayloadDto;
import eu.thompson8.school.app.model.dto.course.list.CourseListDto;
import eu.thompson8.school.app.service.course.CourseService;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("course")
@AllArgsConstructor
public class CourseController {

	private final CourseService service;

	@GetMapping("/{id}")
	public ResponseEntity<CourseDto> getCourseById(@PathVariable("id") Long id) {
		return service.getCourseById(id).map(e -> new ResponseEntity<>(e, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@GetMapping
	public ResponseEntity<CourseListDto> getCourses(@RequestParam(name = "filter", required = false) String filter,
			@RequestParam("page") Integer page, @RequestParam("size") Integer size,
			@RequestParam(name = "sorting", required = false) List<String> sorting) {
		return new ResponseEntity<>(service.getCourses(filter, page, size, sorting), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<CourseDto> createCourse(@RequestBody @Validated CoursePostPayloadDto payload) {
		return new ResponseEntity<>(service.createCourse(payload), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<CourseDto> updateCourse(@PathVariable("id") Long id,
			@RequestBody @Validated CoursePutPayloadDto payload) {
		return new ResponseEntity<>(service.updateCourse(id, payload), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCourse(@PathVariable("id") Long id) {
		service.deleteCourse(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
