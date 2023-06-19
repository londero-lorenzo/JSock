package exceptions;

public class ServerIsAlreadyRunningException extends SocketException{
    public ServerIsAlreadyRunningException(int port) {
        super("Another server is already running at port: " + port + ".");
        this.setExceptionType(ExceptionTypes.ServerIsAlreadyRunningException);
    }
}
