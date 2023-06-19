package socket;

import structures.Message;

public interface Socket {

    boolean send(Message message);

//    public Message receive();

}
