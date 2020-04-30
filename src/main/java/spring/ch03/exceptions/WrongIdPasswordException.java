package spring.ch03.exceptions;

public class WrongIdPasswordException extends RuntimeException {
    public WrongIdPasswordException(String message) {
        super(message);
    }
}
