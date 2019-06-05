package data.exception;

public class WrongContactException extends RepositoryException {
    public WrongContactException(String message) {
        super(message);
    }

    public WrongContactException(String message, Throwable cause) {
        super(message, cause);
    }
}
