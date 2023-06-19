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


    public void connectTo(Address serverAddress) {
        this.serverAddress = serverAddress;
        this.connect();
    }

    private void connect() {
        this.socket = new ClientSocket(this.serverAddress, this.settingsCollector);
        this.setMessageSettings();
    }

    public void send(Message message) {
        this.socket.send(message);
    }

    public Message receive()
    {
        return this.socket.read();
    }

    public void setMessageSettings() {
        if (this.socket.isConnected())
            this.socket.setMessageSettings(this.settingsCollector.getMessageSettings());
        //TODO: invio al server le nuove impostazioni
    }

    public ExceptionHandler getExceptionHandler() {
        return this.exceptionHandler;
    }
}
