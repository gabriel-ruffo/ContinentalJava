package exceptions;

public class InvalidCardValueException extends Exception {

    private static final long serialVersionUID = 7930584532868099077L;

    public InvalidCardValueException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public InvalidCardValueException(String errorMessage) {
        super(errorMessage);
    }
}
