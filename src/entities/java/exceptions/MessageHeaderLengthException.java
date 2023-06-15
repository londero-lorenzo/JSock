package exceptions;

import structures.MessageSettings;

public class MessageHeaderLengthException extends RuntimeException {
    public MessageHeaderLengthException(MessageSettings messageSettings) {
        super("There are not enough header byte to receive the setting message (Header Length: " + messageSettings.getHeaderLength() + ", Header Byte: " + messageSettings.getHeaderSize() + ").");
    }
}
