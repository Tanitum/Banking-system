package Exceptions;

public class ObjectAlreadyExistsException extends IllegalArgumentException {
    public ObjectAlreadyExistsException() {
    }

    public ObjectAlreadyExistsException(String msg) {
        super(msg);
    }
}
