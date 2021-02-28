package br.com.concrete.concreteChallenge.resource;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.concrete.concreteChallenge.model.User;
import br.com.concrete.concreteChallenge.service.UserService;

@RestController
@RequestMapping("users")
public class UserResource {
	
	@Autowired
	private UserService service;

	@GetMapping()
	public ResponseEntity<List<User>> all() {
		return service.all();
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> one(@PathVariable String id) {
		return service.one(id);
	}
	
	@PostMapping()
	public ResponseEntity<Object> create(@RequestBody @Valid User user) {
		return service.create(user);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Object> update(@PathVariable String id,  @RequestBody User user) {
		return service.update(id, user);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<User> delete(@PathVariable String id) {
		return service.delete(id);
	}

}
