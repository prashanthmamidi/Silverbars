package org.credit.suisse.exceptions;

public class InvalidOrderDetailsException extends RuntimeException {
    public InvalidOrderDetailsException(String message) {
        super(message);
    }
}
