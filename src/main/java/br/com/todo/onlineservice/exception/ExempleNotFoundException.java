package br.com.todo.onlineservice.exception;

public class ExempleNotFoundException extends RecordNotFoundException {

	private static final long serialVersionUID = 1L;

	public ExempleNotFoundException() {
		super("Customer not found");
	}

}
