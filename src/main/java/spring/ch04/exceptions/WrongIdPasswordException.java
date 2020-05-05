package spring.ch04.exceptions;

public class WrongIdPasswordException extends RuntimeException {
    public WrongIdPasswordException(String message) {
        super(message);
    }
}
