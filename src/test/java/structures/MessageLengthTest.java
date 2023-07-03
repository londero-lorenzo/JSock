package structures;

import org.junit.jupiter.api.Test;
import structures.Message;
import structures.MessageType;

import java.util.Arrays;

public class MessageLengthTest {

    Message message = new Message("Hello! 1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890", MessageType.TX_MESSAGE);

    @Test
    void messageLength()
    {
        System.out.println(message.getDataLength());
    }

    @Test
    void messageCharLength()
    {
        System.out.println((char) message.getDataLength());
    }
}
