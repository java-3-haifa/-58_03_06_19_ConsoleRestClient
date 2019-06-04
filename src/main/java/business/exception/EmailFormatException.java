package business.exception;

public class EmailFormatException extends ServiceException {
    public EmailFormatException() {
        super("Wrong email format");
    }
}
