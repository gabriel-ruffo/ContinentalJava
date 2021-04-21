package exceptions.points;

import exceptions.GeneralGameException;

public class InvalidPointsException extends GeneralGameException {
    public InvalidPointsException(String errorMessage) {
        super(errorMessage);
    }
}
