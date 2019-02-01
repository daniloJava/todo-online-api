package br.com.todo.onlineservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.todo.onlineservice.model.Exemple;
import br.com.todo.onlineservice.service.ExempleService;

@RestController
@RequestMapping(value = "/exemple", produces = MediaType.APPLICATION_JSON_VALUE)
public class ExempleController {

	@Autowired
	private ExempleService exempleService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Exemple>> findCorVeiculo() {
		List<Exemple> findCorVeiculo = exempleService.findListAll();
		return ResponseEntity.ok(findCorVeiculo);
	}

}
