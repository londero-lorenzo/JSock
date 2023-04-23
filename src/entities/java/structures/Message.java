package structures;

public class Message {

    private final String message;

    private String type;

    public Message(String message, String type) {
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

    public int getLength() {
        return this.message.length();
    }

    public String getType() {
        return this.type;
    }

}


