package exceptions;

public class ConnectionRefusedException extends SocketException {

    public ConnectionRefusedException() {
        super("Connection refused.");
        this.setExceptionType(ExceptionTypes.ConnectionRefused);
    }
}
