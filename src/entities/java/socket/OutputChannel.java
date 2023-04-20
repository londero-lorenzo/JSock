package socket;

import client.ClientSocket;
import structures.Message;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

public class OutputChannel {

    private OutputStream socketOutputStream;

    private ClientSocket socket;

    public OutputChannel(ClientSocket socket) {
        this.socket = socket;
        try {
            this.socketOutputStream = this.socket.getSocketObject().getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void send(Message message) {
        try {
            System.out.println("Header length: " + this.socket.getMessageSettings().getHeaderSize());
            System.out.println("Byte message: " + Arrays.toString(message.toString().getBytes()));
            this.socketOutputStream.write(message.getType().getBytes());
            this.socketOutputStream.write(message.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
