package exceptions;

public class ServerIsAlreadyRunningException extends RuntimeException {
    public ServerIsAlreadyRunningException(int port) {
        super("Another server is already running at port: " + port + ".");
    }
}
