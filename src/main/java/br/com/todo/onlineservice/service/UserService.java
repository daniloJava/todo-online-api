package br.com.todo.onlineservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import br.com.todo.onlineservice.model.User;
import br.com.todo.onlineservice.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public Optional<User> findByPrincipal(UsernamePasswordAuthenticationToken principal) {
		User details = (User) principal.getPrincipal();
		return userRepository.findById(details.getId());

	}

}
