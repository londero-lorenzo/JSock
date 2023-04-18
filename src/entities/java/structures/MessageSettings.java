package structures;

import exceptions.MessageHeaderLengthException;

public class MessageSettings {


    private final int maxHeaderLength;

    private final int header;

    public MessageSettings(int header, int maxHeaderLength) {
        this.header = header;
        this.maxHeaderLength = maxHeaderLength;
        this.checkHeaderLength();
    }


    private void checkHeaderLength() {
        if (this.maxHeaderLength < Integer.toString(this.header).length())
            throw new MessageHeaderLengthException(this);
    }


    public int getHeader() {
        return this.header;
    }

    public int getMaxHeaderLength() {
        return this.maxHeaderLength;
    }

}
