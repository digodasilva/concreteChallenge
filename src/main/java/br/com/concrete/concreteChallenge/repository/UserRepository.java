package br.com.concrete.concreteChallenge.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.concrete.concreteChallenge.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

	Optional<User> findById(String id);

	boolean findByEmail(String email);
	
}
