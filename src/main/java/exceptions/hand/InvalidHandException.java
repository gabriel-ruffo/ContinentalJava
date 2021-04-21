package exceptions.hand;

import exceptions.GeneralGameException;

public class InvalidHandException extends GeneralGameException {
    public InvalidHandException(String errorMessage) {
        super(errorMessage);
    }
}
