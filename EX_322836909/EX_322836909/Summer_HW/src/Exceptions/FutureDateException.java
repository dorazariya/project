package Exceptions;

public class FutureDateException extends Exception {
    public FutureDateException() { super("Future date is not allowed."); }
}