package structures;

import exceptions.MessageHeaderLengthException;

public class MessageSettings {


    private final int header;

    public MessageSettings(int headerByte) {
        this.header = headerByte;
    }


    // TODO: controlli vari
    //private void checkHeaderLength() {
    //    if (this.maxHeaderLength < Integer.toString(this.header).length())
    //        throw new MessageHeaderLengthException(this);
    //}


    public int getHeaderSize() {

        String headerString = "";
        for (int i = 0; i < this.header; i++)
            headerString += "9";
        return Integer.parseInt(headerString);
    }
}
