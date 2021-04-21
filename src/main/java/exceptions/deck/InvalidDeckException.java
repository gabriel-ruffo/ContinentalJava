package exceptions.deck;

import exceptions.GeneralGameException;

public class InvalidDeckException extends GeneralGameException {
    public InvalidDeckException(String errorMessage) {
        super(errorMessage);
    }
}
