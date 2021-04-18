package exceptions.game;

public class InvalidRoundException extends Exception {
    public InvalidRoundException(String errorMessage) {
        super(errorMessage);
    }
}
