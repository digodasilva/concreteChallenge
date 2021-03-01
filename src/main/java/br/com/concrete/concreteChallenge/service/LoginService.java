package br.com.concrete.concreteChallenge.service;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.concrete.concreteChallenge.model.Login;
import br.com.concrete.concreteChallenge.model.Messages;
import br.com.concrete.concreteChallenge.model.User;

@Service
@Transactional
public class LoginService {

	@Autowired
	private UserService userService; 

	public ResponseEntity<Object> in(Login login) {
		Messages message = new Messages("Usuário e/ou senha inválidos.");
		Object objectUser = this.userService.login(login);
		if (Objects.isNull(objectUser)) {
			return new ResponseEntity<Object>(message, HttpStatus.NOT_FOUND);
		}
		User user = (User) objectUser;
		if (user.getPassword().equals(login.getPassword())) {
			user.setLast_login(LocalDateTime.now());
			return new ResponseEntity<Object>(user, HttpStatus.OK);
		}
		return new ResponseEntity<Object>(message, HttpStatus.UNAUTHORIZED);
	}
	
	
}
