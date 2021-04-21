package exceptions.card;

import exceptions.GeneralGameException;

public class InvalidCardException extends GeneralGameException {
    public InvalidCardException(String errorMessage) {
        super(errorMessage);
    }
}
