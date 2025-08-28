package Exceptions;

public class ObjectAlreadyExistsException extends Exception {
    public ObjectAlreadyExistsException(String what) { super(what + " already exists."); }
}