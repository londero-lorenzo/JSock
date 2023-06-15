package client;

import socket.OutputChannel;
import socket.InputChannel;
import socket.Socket;
import structures.Address;
import structures.Message;
import structures.MessageSettings;
import structures.MessageTypes;
import structures.SettingsCollector;
import structures.Logger;

import java.io.IOException;

public class ClientSocket implements Socket {

    private final java.net.Socket socket;

    private final Address address;

    private OutputChannel outputChannel;

    protected InputChannel inputChannel;

    private final SettingsCollector settingsCollector;


    private boolean connected = false;


    public ClientSocket(Address address, SettingsCollector settingsCollector) {
        this.address = address;
        this.settingsCollector = settingsCollector;
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
    }

    @Override
    public void setMessageSettings(MessageSettings messageSettings) {
        this.messageSettings = messageSettings;
        this.messageSettingChanged();
        // TODO: protocollo comunicazione impostazioni
    }

    private void messageSettingChanged() {
        // TODO: add settings separators + line separators
        this.send(new Message(Integer.toString(this.messageSettings.getHeaderLength()) + MessageTypes.LINE_SEPARATOR, MessageTypes.SET_HEADER_LENGTH));

    }

    @Override
    public void send(Message message) {
        this.outputChannel.send(message);
    }

    public String read() {
        return this.inputChannel.read();
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
