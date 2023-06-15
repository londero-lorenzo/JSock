package client;

import structures.Address;
import structures.Message;
import structures.SettingsCollector;


public class Client {
    private Address serverAddress;

    private ClientSocket socket;

    private SettingsCollector settingsCollector;

    public Client(SettingsCollector messageSettings) {
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

    public void setMessageSettings(MessageSettings messageSettings) {
        this.messageSettings = messageSettings;
        if (this.socket.isConnected())
            this.socket.setMessageSettings(this.messageSettings);
        //TODO: invio al server le nuove impostazioni
    }
}
