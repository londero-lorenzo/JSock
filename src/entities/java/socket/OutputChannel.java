package socket;

import client.ClientSocket;

import java.io.IOException;
import java.io.InputStream;

public class OutputChannel {

    private InputStream socketInputStream;

    private ClientSocket socket;


    public OutputChannel(ClientSocket socket) {
        this.socket = socket;
        try {
            this.socketInputStream = this.socket.getSocketObject().getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void read() {
        int data;
        try {
            data = this.socketInputStream.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(data);
    }
}
