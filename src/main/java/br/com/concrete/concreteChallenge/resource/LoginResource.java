package br.com.concrete.concreteChallenge.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.concrete.concreteChallenge.model.Login;
import br.com.concrete.concreteChallenge.service.LoginService;

@RestController
@RequestMapping("login")
public class LoginResource {

	@Autowired
	private LoginService service;
	
	@PostMapping()
	public ResponseEntity<Object> in(@RequestBody Login login) {
		return service.in(login);
	} 
}
