package exceptions.game;

import exceptions.GeneralGameException;

public class InvalidRoundException extends GeneralGameException {
    public InvalidRoundException(String errorMessage) {
        super(errorMessage);
    }
}
