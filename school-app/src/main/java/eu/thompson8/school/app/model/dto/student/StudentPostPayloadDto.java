package eu.thompson8.school.app.model.dto.student;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StudentPostPayloadDto {

	@NotNull
	@Size(min = 1, max = 64)
	private String firstName;

	@NotNull
	@Size(min = 1, max = 64)
	private String lastName;

	@NotNull
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate birthDate;

}
