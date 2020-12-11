package by.dubrovskaya.definition.exception;

public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = 2523068472024599784L;

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
    
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
