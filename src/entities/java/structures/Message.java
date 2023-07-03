package structures;

public class Message {

    private final String message;

    private final MessageType type;

    public Message(String message, MessageType type) {
        this.message = message;
        this.type = type;
    }

    @Override
    public String toString() {
        return this.message;
    }

    public byte[] getBytes() {
        return this.message.getBytes();
    }

    public int getDataLength() {
        return this.message.getBytes().length;
    }

    public int getDataLengthSize() {
        return Integer.toString(this.getDataLength()).length();
    }

    public int getTypeLength() {
        return this.type.length();
    }

    public int getTypeLengthSize() {
        return Integer.toString(this.getTypeLength()).length();
    }

    public MessageType getType() {
        return this.type;
    }

}


