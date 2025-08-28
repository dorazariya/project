package Exceptions;

public class PersonAlreadyExistException extends Exception{

	private static final long serialVersionUID = 1L;
	public PersonAlreadyExistException(String firstName, String lastName) {
		super(firstName+" "+lastName+" already exists in the system, will not be added again.");
	}

}