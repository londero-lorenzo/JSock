package structures;

import exceptions.MessageHeaderLengthException;

public class Message {

    private String message;

    private String type;

    public Message(String message, String type)
    {
        this.message = message;
        this.type = type;
    }


    public int getLength()
    {
        return this.message.length();
    }

}


class MessageTypes{
    public static String TX_MESSAGE = "!MESSAGE";
    public static String RX_NORMAL_MESSAGE = "!N_MSG";
    public static String RX_LONG_MESSAGE = "!L_MSG";
    public static String RX_END_LONG_MESSAGE = "!F_L_MSG";
    public static String RECV_MSG = "!RECIVED";
}
