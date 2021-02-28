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

import br.com.concrete.concreteChallenge.model.Phone;
import br.com.concrete.concreteChallenge.service.PhoneService;

@RestController
@RequestMapping("phones")
public class PhoneResource {

	@Autowired
	PhoneService service;

	@GetMapping()
	public ResponseEntity<List<Phone>> all() {
		return service.all();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Phone> one(@PathVariable String id) {
		return service.one(id);
	}
	
	@PostMapping()
	public ResponseEntity<Phone> create(@RequestBody @Valid Phone phone) {
		return service.create(phone);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Phone> update(@PathVariable String id,  @RequestBody Phone phone) {
		return service.update(id, phone);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Phone> delete(@PathVariable String id) {
		return service.delete(id);
	}

}
