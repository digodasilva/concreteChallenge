package br.com.concrete.concreteChallenge.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import br.com.concrete.concreteChallenge.model.Phone;
import br.com.concrete.concreteChallenge.repository.PhoneRepository;

@Service
public class PhoneService {

	@Autowired
	private PhoneRepository repository;
	
	public ResponseEntity<List<Phone>> all() {
		List<Phone> phones = this.repository.findAll();
		return new ResponseEntity<List<Phone>>(phones, HttpStatus.OK);
	}
	
	public ResponseEntity<Phone> one(String id) {
		Phone phone = this.repository.findById(id).orElse(null);
		return new ResponseEntity<Phone>(phone, 
				phone == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
	}
	
	public ResponseEntity<Phone> create(Phone phone) {
		Phone createdPhone = this.repository.save(phone);
		return new ResponseEntity<Phone>(createdPhone, 
				createdPhone == null ? HttpStatus.BAD_REQUEST : HttpStatus.OK);
		
	}
	
	public ResponseEntity<Phone> update(String id, Phone phone) {
		Phone updatingPhone = repository.findById(id).orElse(null);
		if(updatingPhone == null)
			return new ResponseEntity<Phone>(HttpStatus.NOT_FOUND);
		Phone updatedPhone = this.repository.save(phone);
		return new ResponseEntity<Phone>(updatedPhone, 
				updatedPhone == null ? HttpStatus.BAD_REQUEST : HttpStatus.OK);
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
