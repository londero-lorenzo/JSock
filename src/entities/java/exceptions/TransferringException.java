package exceptions;

public class TransferringException extends SocketException {
    public TransferringException() {
        super("Error transferring data to server.");
    }
}