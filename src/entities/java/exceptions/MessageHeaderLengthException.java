package exceptions;

import structures.Message;
import structures.MessageSettings;

public class MessageHeaderLengthException extends RuntimeException {
    public MessageHeaderLengthException(MessageSettings messageSettings) {
        super("The header message length [" + messageSettings.getHeader() + "] is longer than the maximum header length allowed [" + messageSettings.getMaxHeaderLength() + "]");
    }
}
