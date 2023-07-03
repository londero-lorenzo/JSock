package client;

import exceptions.ExceptionHandler;
import exceptions.ConnectionRefusedException;
import server.MultiConnectionServerSocket;
import socket.OutputChannel;
import socket.InputChannel;
import socket.Socket;
import structures.*;

import java.io.IOException;

/**
 * <h3>ClientSocket for Client object</h3>
 * <br>
 * This is a low level client class in which all the methods that allow to communicate with the socket are defined both on the server side and on the client side. <br>
 *
 * @see client.Client
 * @see MultiConnectionServerSocket#accept()
 */
public class ClientSocket implements Socket {

    private java.net.Socket socket;

    private final Address address;

    private OutputChannel outputChannel;

    private InputChannel inputChannel;

    private final SettingsCollector settingsCollector;

    private final ExceptionHandler exceptionHandler;

    private boolean connected = false;


    /**
     * <h3>ClientSocket constructor client side</h3>
     * <br>
     * This constructor is called whenever a client requests a connection to a server,
     * it is in charge of initializing the client and sending all the essential settings to establish a connection.
     *
     * @param address           {@link Address} of the server
     * @param settingsCollector {@link SettingsCollector} that this client will use
     * @param exceptionHandler  {@link ExceptionHandler} that will handle the exceptions of this client
     * @see #initializeClient()
     * @see #sendInitialSettings()
     */
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


    /**
     * <h3>ClientSocket constructor server side</h3>
     * <br>
     * This constructor is called whenever a server perceives a call from a client,
     * it is in charge of initializing the client and reading all essential settings received by client to establish a connection.
     *
     * @param socket            {@link java.net.Socket} returned by {@link java.net.ServerSocket#accept()}
     * @param settingsCollector {@link SettingsCollector} that this client will use
     * @param exceptionHandler  {@link ExceptionHandler} that will handle the exceptions of this client
     * @see #initializeClient()
     * @see #readInitialSettings()
     */
    public ClientSocket(java.net.Socket socket, SettingsCollector settingsCollector, ExceptionHandler exceptionHandler) {
        this.socket = socket;
        this.address = new Address(socket.getInetAddress().getHostAddress(), socket.getPort());
        this.exceptionHandler = exceptionHandler;
        this.settingsCollector = settingsCollector;
        this.initializeClient();
        this.readInitialSettings();
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


    /**
     * Method used on client side to send initial settings to the server.
     * <br>
     * This method is called whenever a {@link ClientSocket} object is created.
     */
    private void sendInitialSettings() {
        this.sendSettings(new Message(String.valueOf(this.settingsCollector.getMessageSettings().getHeaderLengthSize()), MessageType.SET_HEADER_LENGTH));
        this.sendSettings(new Message(MessageType.LINE_SEPARATOR, MessageType.END_SETTINGS_SEPARATOR));
    }

    /**
     * Method used to send the message settings from client to server and vice versa only if they change.
     *
     * @param messageSettings {@link MessageSettings} to send
     * @return true if the message settings are correctly sent, otherwise false is returned
     * @deprecated At the beginning this method was used instead of {@link #sendInitialSettings()},
     * now it is redundant and useless as it only sends a string including the name and value of setting
     * but never sends a {@link MessageType#END_SETTINGS_SEPARATOR} thus if this method are called from the get-go
     * it put the server in a perpetual wait, otherwise calling this method in a post-client-initialize moment may
     * produce an internal error on the server side. <br>
     * {will be removed in next version} <br>
     * use {@link #sendInitialSettings()} or send all changed settings via {@link #send(Message)} instead.
     */
    @Deprecated
    public boolean setMessageSettings(MessageSettings messageSettings) {
        if (this.messageSettingChanged(this.settingsCollector.getMessageSettings(), messageSettings))
            return this.sendSettings(new Message(String.valueOf(messageSettings.getHeaderLengthSize()), MessageType.SET_HEADER_LENGTH));
        return true;
    }

    /**
     * Method used to send the message settings in a raw way (as string).
     *
     * @param message {@link Message} that includes the setting
     * @return true if the message settings are correctly sent, otherwise false is returned
     */
    private boolean sendSettings(Message message) {
        return this.outputChannel.sendSettings(message);
    }

    /**
     * Method used on server side to receive initial settings from the client.
     *
     * @see InputChannel#readAll()
     */
    public void readInitialSettings() {
        String allSettings = this.inputChannel.readAll();
        MessageSettings oldMessageSettings = this.getSettingsCollector().getMessageSettings();
        this.getSettingsCollector().getMessageSettings().changeSettingsByString(allSettings);
        List<Setting<?>> changedSettings = this.getSettingsCollector().getMessageSettings().getChangedSettings(oldMessageSettings);
        for (Setting<?> changedSetting : changedSettings.getElements()) {
            this.getLogger().logWithPadding("Changed setting: " + changedSetting.getName());
            this.getLogger().logWithPadding("New associated value: " + changedSetting.getValue());
        }
    }


    /**
     * Method used to replace {@link MessageSettings}.
     * This method only checks if header length setting is changed using message settings passed as parameter.
     *
     * @param oldMessageSettings {@link MessageSettings} before the hypothetical change
     * @param newMessageSettings {@link MessageSettings} after the hypothetical change
     * @return boolean value indicating if header length setting has changed
     * @deprecated At the beginning this method was used in combination with {@link #setMessageSettings(MessageSettings)},
     * now it is redundant and useless as it only checks if the value of HeaderLength setting has changed, regardless of that the {@link MessageSettings} will be replaced<br>
     * {will be removed in next version} <br>
     * use {@link #sendInitialSettings()} or send all changed settings via {@link #send(Message)} instead.
     */
    @Deprecated
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
