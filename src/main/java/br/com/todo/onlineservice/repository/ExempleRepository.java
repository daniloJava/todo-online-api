package br.com.todo.onlineservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.todo.onlineservice.model.Exemple;

public interface ExempleRepository extends JpaRepository<Exemple, Integer> {

}
