package br.com.todo.onlineservice.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.todo.onlineservice.filter.FiltersTask;
import br.com.todo.onlineservice.model.Task;
import br.com.todo.onlineservice.model.User;

public interface CustomTaskRepository {

	Page<Task> search(final FiltersTask filter, final Pageable pageable, User user);
}
