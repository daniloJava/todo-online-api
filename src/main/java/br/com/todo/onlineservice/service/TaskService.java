package br.com.todo.onlineservice.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.todo.onlineservice.enumeration.StatusTaskEnum;
import br.com.todo.onlineservice.exception.TaskNotFoundException;
import br.com.todo.onlineservice.filter.FiltersTask;
import br.com.todo.onlineservice.model.Task;
import br.com.todo.onlineservice.repository.TaskRepository;

@Service
public class TaskService {

	@Autowired
	private TaskRepository taskRepository;

	public Task create(final Task model) {
		model.setDateCreate(LocalDateTime.now());
		model.setDateUpdate(LocalDateTime.now());
		model.setStatus(StatusTaskEnum.CREATE);
		return taskRepository.save(model);
	}

	public Task update(final Task model) {
		if (!taskRepository.existsById(model.getId())) {
			throw new TaskNotFoundException();
		}
		model.setDateUpdate(LocalDateTime.now());
		return taskRepository.save(model);
	}

	public Page<Task> search(FiltersTask filter, final Pageable pageable) {
		return taskRepository.search(filter, pageable);
	}

	public Optional<Task> findById(final Integer id) {
		if (!taskRepository.existsById(id)) {
			throw new TaskNotFoundException();
		}
		return taskRepository.findById(id);

	}

}
