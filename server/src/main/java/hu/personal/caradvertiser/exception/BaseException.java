package hu.personal.caradvertiser.exception;

public abstract class BaseException extends RuntimeException {
    private final String field;
    public BaseException(String field, String message) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
