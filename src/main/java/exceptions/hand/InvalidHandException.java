package exceptions.hand;

public class InvalidHandException extends Exception {
    public InvalidHandException(String errorMessage) {
        super(errorMessage);
    }
}
