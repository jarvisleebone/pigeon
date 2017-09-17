package org.pigeon.exception;

public class PigeonException extends Throwable{

    private static final long serialVersionUID = 8978773183141009730L;

    private String message;

    public PigeonException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return this.message;
    }
}
