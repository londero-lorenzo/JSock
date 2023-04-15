package socket;

import structures.Address;
import structures.Message;

import java.io.IOException;

public class Socket {

    private java.net.Socket socket;

    private Address address;

    private InputChannel inputChannel;

    private OutputChannel outputChannel;


    public Socket(Address address)
    {
        this.address = address;
        try {
            this.socket = new java.net.Socket(this.address.getIpv4(), this.address.getPort());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.inputChannel = new InputChannel(this.socket);
        this.outputChannel = new OutputChannel(this.socket);
    }


    public void send(Message message)
    {

    }
}
