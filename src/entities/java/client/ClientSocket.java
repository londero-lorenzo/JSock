package client;

import socket.OutputChannel;
import socket.InputChannel;
import socket.Socket;
import structures.Address;
import structures.Message;
import structures.MessageSettings;
import structures.MessageTypes;

import java.io.IOException;

public class ClientSocket implements Socket {

    private java.net.Socket socket;

    private Address address;

    private OutputChannel outputChannel;

    private InputChannel inputChannel;

    private MessageSettings messageSettings;

    private boolean connected = false;


    public ClientSocket(Address address, MessageSettings messageSettings) {
        this.address = address;
        this.messageSettings = messageSettings;
        try {
            this.socket = new java.net.Socket(this.address.getIpv4(), this.address.getPort());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.initializeClient();
    }


    public ClientSocket(java.net.Socket socket, MessageSettings messageSettings) {
        this.socket = socket;
        this.messageSettings = messageSettings;
        this.initializeClient();
    }

    private void initializeClient() {
        this.outputChannel = new OutputChannel(this);
        this.inputChannel = new InputChannel(this);
        this.connected = true;
        this.messageSettingChanged();
    }

    @Override
    public void setMessageSettings(MessageSettings messageSettings) {
        this.messageSettings = messageSettings;
        // TODO: protocollo comunicazione impostazioni
    }

    private void messageSettingChanged() {
        this.send(new Message(Integer.toString(this.messageSettings.getHeaderSize()), MessageTypes.SET_HEADER_LENGTH));

    }

    @Override
    public void send(Message message) {
        this.outputChannel.send(message);
    }

    public void read() {
        this.inputChannel.read();
    }

    public MessageSettings getMessageSettings() {
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
