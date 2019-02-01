package br.com.todo.onlineservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.todo.onlineservice.model.Exemple;
import br.com.todo.onlineservice.repository.ExempleRepository;

@Service
public class ExempleService {

	@Autowired
	private ExempleRepository exempleRepository;

	public List<Exemple> findListAll() {
		return exempleRepository.findAll();
	}

}
