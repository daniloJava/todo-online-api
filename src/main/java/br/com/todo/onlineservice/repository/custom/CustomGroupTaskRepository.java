package br.com.todo.onlineservice.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.todo.onlineservice.model.GroupTask;

public interface CustomGroupTaskRepository {

	Page<GroupTask> search(final String filter, final Pageable pageable);
}
