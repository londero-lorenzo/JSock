package structures;

public class Message {

    private static final int HEADER = 4;

    private String message;

    private String type;

    public Message(String message, String type)
    {
        this.message = message;
        this.type = type;
    }

}

class MessageTypes{
    public static String TX_MESSAGE = "!MESSAGE";
    public static String RX_NORMAL_MESSAGE = "!N_MSG";
    public static String RX_LONG_MESSAGE = "!L_MSG";
    public static String RX_END_LONG_MESSAGE = "!F_L_MSG";
    public static String RECV_MSG = "!RECIVED";
}
