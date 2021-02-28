package br.com.concrete.concreteChallenge.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.concrete.concreteChallenge.model.Phone;

public interface PhoneRepository extends JpaRepository<Phone, String> {

	Optional<Phone> findById(String id);
	
}
