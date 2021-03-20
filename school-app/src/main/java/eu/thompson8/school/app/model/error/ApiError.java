package eu.thompson8.school.app.model.error;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApiError {

	private String errorMessage;

	private List<Object> errorDetails;

}
