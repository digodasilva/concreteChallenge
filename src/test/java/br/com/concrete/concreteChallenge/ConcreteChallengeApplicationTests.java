package br.com.concrete.concreteChallenge;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.concrete.concreteChallenge.model.Phone;
import br.com.concrete.concreteChallenge.model.User;
import br.com.concrete.concreteChallenge.repository.UserRepository;

@SpringBootTest
class ConcreteChallengeApplicationTests {

	UserRepository repository;
	
	@Test
	void contextLoads() {
	}
	
	@Test
	private void addUsers() {
		User user1 = new User("Rodrigo Silva", "digodasilva@gmail.com", "digodasilva", null);
		Phone phone1 = new Phone("912345678", "67", user1);
		Phone phone2 = new Phone("12345678", "67", user1);
		List<Phone> phones1 = new ArrayList<Phone>();
		phones1.add(phone1);
		phones1.add(phone2);
		user1.setPhones(phones1);
		this.repository.save(user1);
		User user2 = new User("João Silva", "joao@joao.com", "joao", null);
		Phone phone3 = new Phone("87654321", "67", user2);
		List<Phone> phones2 = new ArrayList<Phone>();
		phones2.add(phone3);
		user2.setPhones(phones2);
		this.repository.save(user2);
		
		List<User> users = this.repository.findAll();

		assertEquals(users.size(), 2);
	}

}
