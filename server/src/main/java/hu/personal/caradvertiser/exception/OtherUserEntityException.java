package hu.personal.caradvertiser.exception;

public class OtherUserEntityException extends BaseException {
    public OtherUserEntityException(String field, String message) {
        super(field, message);
    }
}
