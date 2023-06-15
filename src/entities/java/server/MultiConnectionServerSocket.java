package server;

import socket.Socket;
import structures.Address;
import structures.Message;
import structures.MessageSettings;
import structures.SettingsCollector;
import java.io.IOException;
import java.net.InetAddress;

public class MultiConnectionServerSocket implements Socket {


    private final java.net.ServerSocket serverSocket;

    private final Address address;

    private SettingsCollector settingsCollector;

    private final boolean connected = false;


    public MultiConnectionServerSocket(int port, SettingsCollector settingsCollector) {
        this.settingsCollector = settingsCollector;
        try {
            this.serverSocket = new java.net.ServerSocket(port);
            this.address = new Address(InetAddress.getLocalHost().getHostAddress(), port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.settingsCollector.getCurrentSettings().getLogger().logWithTime("Server socket ready on address: " + this.address.getIpv4() + ":" + this.address.getPort());
    }


    public MultiConnectionServerSocket(Address address, SettingsCollector settingsCollector) {
        this.settingsCollector = settingsCollector;
        try {
            this.address = address;
            this.serverSocket = new java.net.ServerSocket(this.address.getPort());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.settingsCollector.getCurrentSettings().getLogger().logWithTime("Server socket ready on address: " + this.address.getIpv4() + ":" + this.address.getPort());
    }

    public ClientSocketServer accept() {
        try {
            // TODO: check if here is the right place to transfer client log (is more useful create a username for the client)
            return new ClientSocketServer(this.serverSocket.accept(), this.settingsCollector);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setMessageSettings(MessageSettings messageSettings) {

    }

    @Override
    public void send(Message message) {

    }

    @Override
    public java.net.Socket getSocketObject() {
        return null;
    }


    //TODO: forse inutile
    @Override
    public boolean isConnected() {
        return this.connected;
    }
}
