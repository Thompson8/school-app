package eu.thompson8.school.app.model.error.validation;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FieldValidationErrorDetail {

	private String field;

	private String errorMessage;

}
