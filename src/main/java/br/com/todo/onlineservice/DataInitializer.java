package br.com.todo.onlineservice;

import java.util.Arrays;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.todo.onlineservice.model.User;
import br.com.todo.onlineservice.repository.UserRepository;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {

	@Autowired
	private UserRepository users;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) throws Exception {

		users.save(User.builder().username("user").password(passwordEncoder.encode("password")).roles(Arrays.asList("ROLE_USER")).build());

		users.save(User.builder().username("admin").password(passwordEncoder.encode("password")).roles(Arrays.asList("ROLE_USER", "ROLE_ADMIN")).build());

		log.debug("printing all users...");
		users.findAll().forEach(v -> log.debug(" User :" + v.toString()));
	}
}
