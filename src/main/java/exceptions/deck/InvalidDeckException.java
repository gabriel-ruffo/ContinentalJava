package exceptions.deck;

public class InvalidDeckException extends Exception {
    public InvalidDeckException(String errorMessage) {
        super(errorMessage);
    }
}
