package br.com.todo.onlineservice.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.todo.onlineservice.model.User;

public interface CustomUserRepository {

	User getActiveUser(String userName);

	Page<User> search(String filter, Pageable pageable);
}
