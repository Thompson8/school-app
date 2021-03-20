package eu.thompson8.school.app.controller.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("user")
public class UserController {

	@PostMapping	
	public ResponseEntity<String> checkUser(){
		return new ResponseEntity<>("OK", HttpStatus.OK);
	}
	
}
