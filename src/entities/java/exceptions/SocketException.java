package exceptions;

public class SocketException extends RuntimeException {
    private ExceptionTypes exceptionType;

    public SocketException(String exceptionMessage) {
        super(exceptionMessage);
        this.exceptionType = ExceptionTypes.ExceptionNotInitialized;
    }

    protected void setExceptionType(ExceptionTypes exceptionType) {
        this.exceptionType = exceptionType;
    }

    public ExceptionTypes getExceptionType() {
        return this.exceptionType;
    }


}
