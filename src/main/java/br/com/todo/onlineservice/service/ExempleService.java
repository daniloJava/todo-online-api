package br.com.todo.onlineservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.todo.onlineservice.exception.ExempleNotFoundException;
import br.com.todo.onlineservice.model.Exemple;
import br.com.todo.onlineservice.repository.ExempleRepository;

@Service
public class ExempleService {

	@Autowired
	private ExempleRepository exempleRepository;

	public Exemple create(final Exemple model) {
		return exempleRepository.save(model);
	}

	public Exemple update(final Exemple model) {
		if (!exempleRepository.existsById(model.getId())) {
			throw new ExempleNotFoundException();
		}

		return exempleRepository.save(model);
	}

	public Page<Exemple> search(final Pageable pageable) {
		return exempleRepository.findAll(pageable);
	}

	public Optional<Exemple> findById(final Integer id) {
		if (!exempleRepository.existsById(id)) {
			throw new ExempleNotFoundException();
		}
		return exempleRepository.findById(id);

	}

}
