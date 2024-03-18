package hu.personal.caradvertiser.exception;

public class EntityNotFoundException extends BaseException {
    public EntityNotFoundException(String field, String message) {
        super(field, message);
    }
}
