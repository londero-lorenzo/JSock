package client;

import socket.InputChannel;
import socket.OutputChannel;
import socket.Socket;
import structures.Address;
import structures.Message;
import structures.MessageSettings;

import java.io.IOException;

public class ClientSocket implements Socket {

    private java.net.Socket socket;

    private Address address;

    private InputChannel inputChannel;

    private OutputChannel outputChannel;

    private MessageSettings messageSettings;

    private boolean connected = false;


    public ClientSocket(Address address, MessageSettings messageSettings) {
        this.address = address;
        this.messageSettings =messageSettings;
        try {
            this.socket = new java.net.Socket(this.address.getIpv4(), this.address.getPort());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.initializeClient();
    }


    public ClientSocket(java.net.Socket socket, MessageSettings messageSettings) {
        this.socket = socket;
        this.messageSettings =messageSettings;
        this.initializeClient();
    }

    private void initializeClient() {
        this.inputChannel = new InputChannel(this);
        this.outputChannel = new OutputChannel(this);
        this.connected = true;
    }

    @Override
    public void setMessageSettings(MessageSettings messageSettings) {
        this.messageSettings = messageSettings;

        // TODO: protocollo comunicazione impostazioni


    }

    @Override
    public void send(Message message) {
        this.inputChannel.send(null);
    }

    public void read()
    {
        this.outputChannel.read();
    }

    public MessageSettings getMessageSettings()
    {
        return this.messageSettings;
    }

    @Override
    public java.net.Socket getSocketObject() {
        return this.socket;
    }

    @Override
    public boolean isConnected() {
        return this.connected;
    }
}
