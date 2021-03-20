package eu.thompson8.school.app.controller.health;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import eu.thompson8.school.app.model.health.Health;
import eu.thompson8.school.app.model.health.HealthStatus;

@Controller
@RequestMapping("health")
public class HealthController {

	@GetMapping
	public ResponseEntity<Health> checkHealth() {
		Health result = new Health();
		result.setStatus(HealthStatus.UP);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

}
