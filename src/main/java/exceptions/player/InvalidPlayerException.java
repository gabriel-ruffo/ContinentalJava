package exceptions.player;

import exceptions.GeneralGameException;

public class InvalidPlayerException extends GeneralGameException {
    public InvalidPlayerException(String errorMessage) {
        super(errorMessage);
    }
}
