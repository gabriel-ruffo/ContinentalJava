package exceptions.points;

public class InvalidPointsException extends Exception {
    public InvalidPointsException(String errorMessage) {
        super(errorMessage);
    }
}
