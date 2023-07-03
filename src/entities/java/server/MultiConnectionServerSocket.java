package server;

import client.ClientSocket;
import exceptions.ExceptionHandler;
import socket.Socket;
import structures.Address;
import structures.Message;
import structures.SettingsCollector;

import java.io.IOException;
import java.net.InetAddress;

public class MultiConnectionServerSocket implements Socket {

    private final java.net.ServerSocket serverSocket;

    private final Address address;

    private final SettingsCollector settingsCollector;

    private final ExceptionHandler exceptionHandler;

    private final boolean connected = false;

    public MultiConnectionServerSocket(int port, SettingsCollector settingsCollector, ExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
        this.settingsCollector = settingsCollector;
        try {
            this.serverSocket = new java.net.ServerSocket(port);
            this.address = new Address(InetAddress.getLocalHost().getHostAddress(), port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.settingsCollector.getCurrentSettings().getLogger().logWithTime("Server socket ready on address: " + this.address.getIpv4() + ":" + this.address.getPort());
    }


    public MultiConnectionServerSocket(Address address, SettingsCollector settingsCollector, ExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
        this.settingsCollector = settingsCollector;
        try {
            this.address = address;
            this.serverSocket = new java.net.ServerSocket(this.address.getPort());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.settingsCollector.getCurrentSettings().getLogger().logWithTime("Server socket ready on address: " + this.address.getIpv4() + ":" + this.address.getPort());
    }

    public ClientSocket accept() {
        try {
            // TODO: check if here is the right place to transfer client log (is more useful create a username for the client)
            return new ClientSocket(this.serverSocket.accept(), this.settingsCollector, this.exceptionHandler);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // TODO: useful to send a jam message to all clients connected
    @Override
    public boolean send(Message message) {
        return true;
    }
}
