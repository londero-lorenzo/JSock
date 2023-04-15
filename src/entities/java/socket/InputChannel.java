package socket;

import structures.Message;

import java.io.IOException;
import java.io.OutputStream;

public class InputChannel {

    private OutputStream socketOutputStream;


    public InputChannel(java.net.Socket socket)
    {
        try {
            this.socketOutputStream = socket.getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void send(Message message)
    {

    }
}
