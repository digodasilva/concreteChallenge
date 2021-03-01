package br.com.concrete.concreteChallenge.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;

import br.com.concrete.concreteChallenge.model.Messages;
import br.com.concrete.concreteChallenge.model.Phone;
import br.com.concrete.concreteChallenge.repository.PhoneRepository;

@Service
@Transactional
public class PhoneService {

	@Autowired
	private PhoneRepository repository;
	
	@Autowired
	private UserService userService;
	
	public ResponseEntity<List<Phone>> all() {
		List<Phone> phones = this.repository.findAll();
		return new ResponseEntity<List<Phone>>(phones, HttpStatus.OK);
	}
	
	public ResponseEntity<Phone> one(String id) {
		Phone phone = this.repository.findById(id).orElse(null);
		return new ResponseEntity<Phone>(phone, 
				phone == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
	}
	
	public ResponseEntity<Object> create(Phone phone) {
		Messages message = new Messages("");
		if ((userService.one(phone.getUser().getId()).getStatusCode() == HttpStatus.OK)) {
			if (Objects.isNull(phone)) {
				message = new Messages("Telefone não informado.");
				return new ResponseEntity<Object>(message, HttpStatus.NOT_FOUND);
			}
			if (Objects.isNull(phone.getUser()) || Objects.isNull(phone.getUser().getId())) {
				message = new Messages("Telefone não associado a um usuário.");
				return new ResponseEntity<Object>(message, HttpStatus.NOT_FOUND);
			}
			Phone createdPhone = this.repository.save(phone);
			return new ResponseEntity<Object>(createdPhone, 
					createdPhone == null ? HttpStatus.BAD_REQUEST : HttpStatus.OK);
		} else {
			message = new Messages("Usuário não encontrado.");
			return new ResponseEntity<Object>(message, HttpStatus.NOT_FOUND);
		}
	}
	
	public ResponseEntity<Object> update(String id, Phone phone) {
		if ((userService.one(phone.getUser().getId()).getStatusCode() == HttpStatus.OK)) {
			Phone updatingPhone = this.repository.findById(id).orElse(null);
			if(updatingPhone == null)
				return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
			Phone updatedPhone = this.repository.save(phone);
			return new ResponseEntity<Object>(updatedPhone, 
					updatedPhone == null ? HttpStatus.BAD_REQUEST : HttpStatus.OK);
		} else {
			Messages message = new Messages("Usuário não encontrado.");
			return new ResponseEntity<Object>(message, HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Phone> delete(String id) {
		Phone deletingPhone = repository.findById(id).orElse(null);
		if(deletingPhone == null)
			return new ResponseEntity<Phone>(HttpStatus.NOT_FOUND);
		this.repository.deleteById(id);
		return new ResponseEntity<Phone>(HttpStatus.OK);
	}
}
