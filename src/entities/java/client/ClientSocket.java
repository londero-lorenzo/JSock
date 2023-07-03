package client;

import exceptions.ExceptionHandler;
import exceptions.ConnectionRefusedException;
import server.MultiConnectionServerSocket;
import socket.OutputChannel;
import socket.InputChannel;
import socket.Socket;
import structures.*;

import java.io.IOException;

public class ClientSocket implements Socket {

    private java.net.Socket socket;

    private final Address address;

    private OutputChannel outputChannel;

    private InputChannel inputChannel;

    private final SettingsCollector settingsCollector;

    private final ExceptionHandler exceptionHandler;

    private boolean connected = false;


    public ClientSocket(Address address, SettingsCollector settingsCollector, ExceptionHandler exceptionHandler) {
        this.address = address;
        this.exceptionHandler = exceptionHandler;
        this.settingsCollector = settingsCollector;
        try {
            this.socket = new java.net.Socket(this.address.getIpv4(), this.address.getPort());
        } catch (IOException e) {
            this.socket = null;
            this.exceptionHandler.setException(new ConnectionRefusedException());
            return;
        }
        this.initializeClient();
        this.sendInitialSettings();
    }


    public ClientSocket(java.net.Socket socket, SettingsCollector settingsCollector, ExceptionHandler exceptionHandler) {
        this.socket = socket;
        this.address = new Address(socket.getInetAddress().getHostAddress(), socket.getPort());
        this.exceptionHandler = exceptionHandler;
        this.settingsCollector = settingsCollector;
        this.initializeClient();
        // TODO: set client logger file with the right name (e.g. 192.168.1.25-ClientLog.txt or wait for name: <client_name>-ClientLog.txt)
    }

    private void initializeClient() {
        this.getLogger().logWithTime("Connection to: " + this.address);
        this.getLogger().logWithPadding("Client initialization:");
        this.outputChannel = new OutputChannel(this);
        this.getLogger().logWithPadding(" -> Output channel: established");
        this.inputChannel = new InputChannel(this);
        this.getLogger().logWithPadding(" -> Input channel: established");
        this.connected = true;
    }

    private void sendInitialSettings() {
        this.sendSettings(new Message(String.valueOf(this.settingsCollector.getMessageSettings().getHeaderLengthSize()), MessageType.SET_HEADER_LENGTH));
        this.sendSettings(new Message(MessageType.LINE_SEPARATOR, MessageType.END_SETTINGS_SEPARATOR));
    }

    public boolean setMessageSettings(MessageSettings messageSettings) {
        if (this.messageSettingChanged(this.settingsCollector.getMessageSettings(), messageSettings))
            return this.sendSettings(new Message(String.valueOf(messageSettings.getHeaderLengthSize()), MessageType.SET_HEADER_LENGTH));
        return true;
    }

    private boolean messageSettingChanged(MessageSettings oldMessageSettings, MessageSettings newMessageSettings) {
        this.settingsCollector.remove(oldMessageSettings);
        this.settingsCollector.add(newMessageSettings);
        return newMessageSettings.HeaderLengthHasChanged(oldMessageSettings);
    }


    @Override
    public boolean send(Message message) {
        return this.outputChannel.send(message);
    }


    public Message read() {
        return this.inputChannel.read();
    }

    public ExceptionHandler getExceptionHandler() {
        return this.exceptionHandler;
    }

    public MessageSettings getMessageSettings() {
        return this.settingsCollector.getMessageSettings();
    }

    public Logger getLogger() {
        return this.settingsCollector.getCurrentSettings().getLogger();
    }

    public SettingsCollector getSettingsCollector() {
        return settingsCollector;
    }

    public java.net.Socket getSocketObject() {
        return this.socket;
    }

    public boolean isConnected() {
        return this.connected;
    }
}
