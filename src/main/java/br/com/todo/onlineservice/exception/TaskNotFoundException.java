package br.com.todo.onlineservice.exception;

public class TaskNotFoundException extends RecordNotFoundException {

	private static final long serialVersionUID = 1L;

	public TaskNotFoundException() {
		super("Customer not found");
	}

}
