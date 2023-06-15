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
        this.getLogger().logWithTime("Connessione a: " + this.address);
        this.getLogger().logWithPadding("Inizializzazione client:");
        this.outputChannel = new OutputChannel(this);
        this.getLogger().logWithPadding(" -> Canale in uscita inizializzato...");
        this.inputChannel = new InputChannel(this);
        this.getLogger().logWithPadding(" -> Canale in ingresso inizializzato...");
        this.connected = true;
    }

    private void sendInitialSettings() {
        this.sendSettings(new Message(String.valueOf(this.settingsCollector.getMessageSettings().getHeaderLength()), MessageTypes.SET_HEADER_LENGTH));
        this.sendSettings(new Message(MessageTypes.LINE_SEPARATOR, MessageTypes.END_SETTINGS_SEPARATOR));
    }

    @Override
    public void setMessageSettings(MessageSettings messageSettings) {
        if (this.messageSettingChanged(this.settingsCollector.getMessageSettings(), messageSettings))
            this.sendSettings(new Message(String.valueOf(messageSettings.getHeaderLength()), MessageTypes.SET_HEADER_LENGTH));

        // TODO: protocollo comunicazione impostazioni
    }

    private boolean messageSettingChanged(MessageSettings oldMessageSettings, MessageSettings newMessageSettings) {
        this.settingsCollector.remove(oldMessageSettings);
        this.settingsCollector.add(newMessageSettings);
        // TODO: add settings separators + line separators
        return newMessageSettings.HeaderLengthHasChanged(oldMessageSettings);
        // messaggio utilizzato per indicare la fine della trasmissione destinata per le impostazioni


    }

    public void sendSettings(Message message) {
        this.outputChannel.sendSettings(message);
    }

    @Override
    public void send(Message message) {
        this.outputChannel.send(message);
    }


    public Message read() {
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
