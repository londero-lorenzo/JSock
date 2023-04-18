package socket;

import client.ClientSocket;
import structures.Message;

import java.io.IOException;
import java.io.OutputStream;

public class InputChannel {

    private OutputStream socketOutputStream;

    private ClientSocket socket;

    public InputChannel(ClientSocket socket) {
        this.socket = socket;
        try {
            this.socketOutputStream = this.socket.getSocketObject().getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void send(Message message) {
        try {
            System.out.println(this.socket.getMessageSettings().getHeader());
            this.socketOutputStream.write(this.socket.getMessageSettings().getHeader());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
