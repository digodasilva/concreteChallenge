package br.com.concrete.concreteChallenge.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;

import br.com.concrete.concreteChallenge.model.Messages;
import br.com.concrete.concreteChallenge.model.Phone;
import br.com.concrete.concreteChallenge.model.User;
import br.com.concrete.concreteChallenge.repository.UserRepository;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository repository;
	
	public ResponseEntity<List<User>> all() {
		List<User> users = this.repository.findAll();
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}
	
	public ResponseEntity<User> one(String id) {
		User user = this.repository.findById(id).orElse(null);
		return new ResponseEntity<User>(user, 
				user == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
	}
	
	@Transactional(rollbackFor = { SQLException.class })
	public ResponseEntity<Object> create(User user) {
		User createdUser = null;
		if (validateEmail(user)) {
			Messages message = new Messages("E-mail já existente.");
			return new ResponseEntity<Object>(message, HttpStatus.CONFLICT);
		} else {
			createdUser = this.repository.save(user);
			List<Phone> phones = user.getPhones();
			createOrUpdatePhones(phones, createdUser);
		}
		return new ResponseEntity<Object>(createdUser, 
				createdUser == null ? HttpStatus.BAD_REQUEST : HttpStatus.OK);
		
	}
	
	@Transactional(rollbackFor = { SQLException.class })
	public ResponseEntity<Object> update(String id, User user) {
		User updatedUser = null;
		if (validateEmail(user)) {
			Messages message = new Messages("E-mail já existente.");
			return new ResponseEntity<Object>(message, HttpStatus.CONFLICT);
		} else {
			User updatingUser = this.repository.findById(id).orElse(null);
			if(updatingUser == null)
				return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
			updatedUser = this.repository.save(user);
		}
		return new ResponseEntity<Object>(updatedUser, 
				updatedUser == null ? HttpStatus.BAD_REQUEST : HttpStatus.OK);
	}
	
	private boolean validateEmail(User user) {
		if (user.getEmail() != null)
			return repository.findByEmail(user.getEmail());
		return false;
	}

	public void createOrUpdatePhones(List<Phone> phones, User user) {
		if (!phones.isEmpty()) {
			for (Phone phone : phones) {
				phone.setUser(user);
				if (phone.getId() == null)
					new PhoneService().create(phone);
				else
					new PhoneService().update(phone.getId(), phone);
			}
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<User> delete(String id) {
		User deletingUser = this.repository.findById(id).orElse(null);
		if(deletingUser == null)
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		this.repository.deleteById(id);
		return new ResponseEntity<User>(HttpStatus.OK);
	}
}
