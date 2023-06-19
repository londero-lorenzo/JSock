package exceptions;

import structures.Logger;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionHandler {
    private SocketException lastException;

    public void printError(Logger logger) {
        StringWriter errors = new StringWriter();
        lastException.printStackTrace(new PrintWriter(errors));
        logger.log(errors.toString());
    }

    public void setException(SocketException socketException) {
        this.lastException = socketException;
    }

    public void setExceptionFromAnotherExceptionHandler(ExceptionHandler handler) {
        this.setException(handler.getLastExceptionType());
    }

    public SocketException getLastExceptionType() {
        return lastException;
    }
}
