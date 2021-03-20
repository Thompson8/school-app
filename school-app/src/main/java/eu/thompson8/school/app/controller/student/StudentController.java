package eu.thompson8.school.app.controller.student;

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

import eu.thompson8.school.app.model.dto.student.StudentPostPayloadDto;
import eu.thompson8.school.app.model.dto.student.StudentDto;
import eu.thompson8.school.app.model.dto.student.StudentPutPayloadDto;
import eu.thompson8.school.app.model.dto.student.list.StudentListDto;
import eu.thompson8.school.app.service.student.StudentService;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("student")
@AllArgsConstructor
public class StudentController {

	private final StudentService service;

	@GetMapping("/{id}")
	public ResponseEntity<StudentDto> getStudentById(@PathVariable("id") Long id) {
		return service.getStudentById(id).map(e -> new ResponseEntity<>(e, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@GetMapping
	public ResponseEntity<StudentListDto> getStudents(@RequestParam(name = "filter", required = false) String filter,
			@RequestParam("page") Integer page, @RequestParam("size") Integer size,
			@RequestParam(name = "sorting", required = false) List<String> sorting) {
		return new ResponseEntity<>(service.getStudents(filter, page, size, sorting), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<StudentDto> createStudent(@RequestBody @Validated StudentPostPayloadDto payload) {
		return new ResponseEntity<>(service.createStudent(payload), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<StudentDto> updateStudent(@PathVariable("id") Long id,
			@RequestBody @Validated StudentPutPayloadDto payload) {
		return new ResponseEntity<>(service.updateStudent(id, payload), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteStudent(@PathVariable("id") Long id) {
		service.deleteStudent(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
