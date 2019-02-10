package br.com.todo.onlineservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.todo.onlineservice.model.User;
import br.com.todo.onlineservice.repository.custom.CustomUserRepository;

public interface UserRepository extends JpaRepository<User, Integer>, CustomUserRepository {

	Optional<User> findByUsername(String username);

}
