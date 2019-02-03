package br.com.todo.onlineservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.todo.onlineservice.model.Task;
import br.com.todo.onlineservice.repository.custom.CustomTaskRepository;

public interface TaskRepository extends JpaRepository<Task, Integer>, CustomTaskRepository {

}
