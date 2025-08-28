package Exceptions;

public class InvalidBirthDateException extends Exception {
	

	private static final long serialVersionUID = 1L;

	public InvalidBirthDateException() {
		super("The provided birth date is invalid.");
	}
}
