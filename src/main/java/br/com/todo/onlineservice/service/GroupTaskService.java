package br.com.todo.onlineservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.todo.onlineservice.exception.GroupTaskNotFoundException;
import br.com.todo.onlineservice.model.GroupTask;
import br.com.todo.onlineservice.repository.GroupTaskRepository;

@Service
public class GroupTaskService {

	@Autowired
	private GroupTaskRepository groupTaskRepository;

	public GroupTask create(final GroupTask model) {
		return groupTaskRepository.save(model);
	}

	public GroupTask update(final GroupTask model) {
		if (!groupTaskRepository.existsById(model.getId())) {
			throw new GroupTaskNotFoundException();
		}
		return groupTaskRepository.save(model);
	}

	public Page<GroupTask> search(String filter, final Pageable pageable) {
		return groupTaskRepository.search(filter, pageable);
	}

	public Optional<GroupTask> findById(final Integer id) {
		if (!groupTaskRepository.existsById(id)) {
			throw new GroupTaskNotFoundException();
		}
		return groupTaskRepository.findById(id);

	}

}
