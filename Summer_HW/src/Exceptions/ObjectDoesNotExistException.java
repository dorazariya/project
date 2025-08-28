package Exceptions;
public class ObjectDoesNotExistException extends Exception {
    public ObjectDoesNotExistException(String what) { super(what + " does not exist."); }
}
