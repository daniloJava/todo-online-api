package br.com.todo.onlineservice.exception;

public class GroupTaskNotFoundException extends RecordNotFoundException {

	private static final long serialVersionUID = 1L;

	public GroupTaskNotFoundException() {
		super("Customer not found");
	}

}
