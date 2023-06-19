package structures;

public class Message {

    private final String message;

    private final MessageTypes type;

    public Message(String message, MessageTypes type) {
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

    public int getTypeLength() {
        return this.type.length();
    }

    public MessageTypes getType() {
        return this.type;
    }

}


