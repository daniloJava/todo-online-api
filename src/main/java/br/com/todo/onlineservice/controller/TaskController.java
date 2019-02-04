
package br.com.todo.onlineservice.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.todo.onlineservice.filter.FiltersTask;
import br.com.todo.onlineservice.model.Task;
import br.com.todo.onlineservice.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping(value = "/task", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = { "Task" })
public class TaskController {

	@Autowired
	private TaskService taskService;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Create a new Task", response = Integer.class)
	public Integer create(@RequestBody @Valid final Task task) {
		return taskService.create(task).getId();
	}

	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Update the task by id")
	public void update(@RequestBody @Valid final Task task, @PathVariable final Integer id) {
		task.setId(id);
		taskService.update(task);
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Retrieve the task by id")
	public Optional<Task> findById(@ApiParam(value = "Unique identifier", required = true) @PathVariable final Integer id) {
		return taskService.findById(id);
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Retrieve a list of paged tasks")
	@ApiImplicitParams({ //
			@ApiImplicitParam(name = "page", dataType = "int", paramType = "query", value = "Results page to retrieve (0..N)"), //
			@ApiImplicitParam(name = "size", dataType = "int", paramType = "query", value = "Number of tasks per page") //
	})
	public Page<Task> search(FiltersTask filter, final @ApiIgnore Pageable pageable) {
		return taskService.search(filter, pageable);
	}

}
