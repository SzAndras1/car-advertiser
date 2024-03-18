package hu.personal.caradvertiser.exception;

public class NotValidException extends BaseException {
    public NotValidException(String field, String message) {
        super(field, message);
    }
}
