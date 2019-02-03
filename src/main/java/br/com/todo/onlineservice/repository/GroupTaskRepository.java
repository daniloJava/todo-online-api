package br.com.todo.onlineservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.todo.onlineservice.model.GroupTask;

public interface GroupTaskRepository extends JpaRepository<GroupTask, Integer> {

}
