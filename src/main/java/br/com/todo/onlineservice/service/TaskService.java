package br.com.todo.onlineservice.service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import br.com.todo.onlineservice.enumeration.StatusTaskEnum;
import br.com.todo.onlineservice.exception.TaskNotFoundException;
import br.com.todo.onlineservice.filter.FiltersTask;
import br.com.todo.onlineservice.model.GroupTask;
import br.com.todo.onlineservice.model.Task;
import br.com.todo.onlineservice.model.User;
import br.com.todo.onlineservice.repository.GroupTaskRepository;
import br.com.todo.onlineservice.repository.TaskRepository;

@Service
public class TaskService {

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private GroupTaskRepository groupTaskRepository;

	public Task create(final Task model, UsernamePasswordAuthenticationToken principal) {
		model.setDateCreate(LocalDateTime.now());
		model.setDateUpdate(LocalDateTime.now());
		model.setStatus(StatusTaskEnum.CREATE);
		User user = userService.findByPrincipal(principal).get();
		model.setCreatedBy(user);
		model.setLastModifiedBy(user);
		model.setGroup(initGroupTask(model.getGroup()));
		return taskRepository.save(model);
	}

	public Task update(Task model, UsernamePasswordAuthenticationToken principal) {
		if (!taskRepository.existsById(model.getId())) {
			throw new TaskNotFoundException();
		}
		Task task = taskRepository.findById(model.getId()).get();
		task.setLastModifiedBy(userService.findByPrincipal(principal).get());
		task.setDescription(model.getDescription());
		task.setTitle(model.getTitle());
		task.setDateUpdate(LocalDateTime.now());
		return taskRepository.save(task);
	}

	public Page<Task> search(FiltersTask filter, final Pageable pageable, UsernamePasswordAuthenticationToken principal) {
		return taskRepository.search(filter, pageable, (User) principal.getPrincipal());
	}

	public Optional<Task> findById(final Integer id, Principal principal) {
		if (!taskRepository.existsById(id)) {
			throw new TaskNotFoundException();
		}
		return taskRepository.findById(id);

	}

	public void deleteById(final Integer id, Principal principal) {
		if (!taskRepository.existsById(id)) {
			throw new TaskNotFoundException();
		}
		taskRepository.deleteById(id);
	}

	private GroupTask initGroupTask(GroupTask model) {
		Integer id = 1;
		if (model != null) {
			id = model.getId();
		}

		Optional<GroupTask> optional = groupTaskRepository.findById(id);
		GroupTask groupTask;
		if (optional.isPresent()) {
			groupTask = optional.get();
		} else {
			groupTask = groupTaskRepository.save(new GroupTask("New task"));
		}
		return groupTask;
	}

}
