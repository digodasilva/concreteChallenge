package br.com.concrete.concreteChallenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan({"br.com.concrete.concreteChallenge", "br.com.concrete.concreteChallenge.resource", "br.com.concrete.concreteChallenge.repository", "br.com.concrete.concreteChallenge.service", "br.com.concrete.concreteChallenge.model"})
@EntityScan(basePackages = {"br.com.concrete.concreteChallenge.model"})
@EnableJpaRepositories(basePackages = {"br.com.concrete.concreteChallenge.repository"})
@EnableTransactionManagement
public class ConcreteChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConcreteChallengeApplication.class, args);
	}

}
