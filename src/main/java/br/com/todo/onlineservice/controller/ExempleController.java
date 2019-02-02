package br.com.todo.onlineservice.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.todo.onlineservice.model.Exemple;
import br.com.todo.onlineservice.service.ExempleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(value = "/exemple", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = { "Exemple" })
public class ExempleController {

	@Autowired
	private ExempleService exempleService;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Create a new Exemple", response = Integer.class)
	public Integer create(@RequestBody @Valid final Exemple exemple) {
		return exempleService.create(exemple).getId();
	}

	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Update the exemple by id")
	public void update(@RequestBody @Valid final Exemple exemple, @PathVariable final Integer id) {
		exemple.setId(id);
		exempleService.update(exemple);
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Retrieve the exemple by id")
	public Optional<Exemple> findById(@ApiParam(value = "Unique identifier", required = true) @PathVariable final Integer id) {
		return exempleService.findById(id);
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Retrieve a list of paged exemples")
	@ApiImplicitParams({ //
			@ApiImplicitParam(name = "page", dataType = "int", paramType = "query", value = "Results page to retrieve (0..N)"), //
			@ApiImplicitParam(name = "size", dataType = "int", paramType = "query", value = "Number of exemples per page") //
	})
	public Page<Exemple> search(final @ApiIgnore Pageable pageable) {
		return exempleService.search(pageable);
	}

}
