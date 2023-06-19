package client;

import exceptions.ExceptionHandler;
import structures.Address;
import structures.Logger;
import structures.Message;
import structures.SettingsCollector;


public abstract class Client {
    private Address serverAddress;

    private ClientSocket socket;

    private final SettingsCollector settingsCollector;

    private final ExceptionHandler exceptionHandler;

    public Client(SettingsCollector messageSettings) {
        this.exceptionHandler = new ExceptionHandler();
        this.settingsCollector = messageSettings;
    }

    public boolean connectTo(Address serverAddress) {
        if (serverAddress == null)
            return false;
        if (!serverAddress.isUsable()) {
            this.exceptionHandler.setExceptionFromAnotherExceptionHandler(serverAddress.getExceptionHandler());
            return false;
        }
        this.serverAddress = serverAddress;
        return this.connect();
    }

    private boolean connect() {
        this.socket = new ClientSocket(this.serverAddress, this.settingsCollector, this.exceptionHandler);
        if (!socket.isConnected())
            return false;
        return this.setMessageSettings();
    }

    public void send(Message message) {
        this.socket.send(message);
    }

    public Message receive()
    {
        return this.socket.read();
    }

    public boolean setMessageSettings() {
        return this.socket.setMessageSettings(this.settingsCollector.getMessageSettings());
        //TODO: invio al server le nuove impostazioni
    }

    public Logger getLogger() {
        return this.settingsCollector.getCurrentSettings().getLogger();
    }

    public ExceptionHandler getExceptionHandler() {
        return this.exceptionHandler;
    }
}
