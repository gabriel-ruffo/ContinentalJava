package exceptions.player;

public class InvalidPlayerException extends Exception {
    public InvalidPlayerException(String errorMessage) {
        super(errorMessage);
    }
}
