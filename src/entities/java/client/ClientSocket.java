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
        this.sendInitialSettings();
    }


    public ClientSocket(java.net.Socket socket, SettingsCollector settingsCollector) {
        this.socket = socket;
        this.address = new Address(socket.getInetAddress().getHostAddress(), socket.getPort());
        this.settingsCollector = settingsCollector;
        this.initializeClient();
        // TODO: set client logger file with the right name (e.g. 192.168.1.25-ClientLog.txt or wait for name: <client_name>-ClientLog.txt)
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
