package exceptions;

public class TransferringSettingsException extends SocketException{
    public TransferringSettingsException() {
        super("Error transferring settings to server.");
    }
}
