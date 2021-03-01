package br.com.concrete.concreteChallenge.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;

import br.com.concrete.concreteChallenge.model.Login;
import br.com.concrete.concreteChallenge.model.Messages;
import br.com.concrete.concreteChallenge.model.Phone;
import br.com.concrete.concreteChallenge.model.User;
import br.com.concrete.concreteChallenge.repository.UserRepository;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private PhoneService phoneService;
	
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
		if (existsEmail(user)) {
			Messages message = new Messages("E-mail já existente.");
			return new ResponseEntity<Object>(message, HttpStatus.CONFLICT);
		} else {
			createdUser = this.repository.save(user);
			List<Phone> phones = user.getPhones();
			createPhones(phones, createdUser);
		}
		return new ResponseEntity<Object>(createdUser, 
				createdUser == null ? HttpStatus.BAD_REQUEST : HttpStatus.OK);
		
	}
	
	@Transactional(rollbackFor = { SQLException.class })
	public ResponseEntity<Object> update(String id, User user) {
		User updatedUser = null;
		if (existsEmail(user)) {
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
	
	private boolean existsEmail(User user) {
		if (!Objects.isNull(user.getEmail())) {
			Optional<User> foundEmail = repository.findByEmail(user.getEmail());
			if (!Objects.isNull(user.getId()) && foundEmail.isPresent() && 
					foundEmail.get().getId().equals(user.getId())) {
				return false;
			} else {
				return foundEmail.isPresent();
			}
		}
		return true;
	}

	public void createPhones(List<Phone> phones, User user) {
		if (!phones.isEmpty()) {
			for (Phone phone : phones) {
				phone.setUser(user);
				this.phoneService.create(phone);
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

	public Object login(Login login) {
		if (Objects.isNull(login)) {
			return null;
		}
		Optional<User> loggingUser = this.repository.findByEmail(login.getEmail());
		if (loggingUser.isPresent()) {
			return (Object) loggingUser.get();
		}
		return null;
	}
	
	public User getByEmail(String email) {
		Optional<User> user  = repository.findByEmail(email);
		return user.get();
		
	}
}
