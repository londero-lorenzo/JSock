package server;

import socket.Socket;
import structures.Address;
import structures.Message;
import structures.MessageSettings;
import structures.SettingsCollector;
import java.io.IOException;
import java.net.InetAddress;

public class MultiConnectionServerSocket implements Socket {


    private java.net.ServerSocket serverSocket;

    private Address address;

    private OutputChannel outputChannel;

    private InputChannel inputChannel;

    private MessageSettings messageSettings;

    private boolean connected = false;


    public MultiConnectionServerSocket(int port, MessageSettings messageSettings) {
        this.messageSettings = messageSettings;
        try {
            this.serverSocket = new java.net.ServerSocket(port);
            this.address = new Address(InetAddress.getLocalHost().getHostAddress(), port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Server avviato all'indirizzo: " + this.address.getIpv4() + " sulla porta: " + this.address.getPort());
    }


    public MultiConnectionServerSocket(Address address, MessageSettings messageSettings) {
        this.messageSettings = messageSettings;
        try {
            this.address = address;
            this.serverSocket = new java.net.ServerSocket(this.address.getPort());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Server avviato all'indirizzo: " + this.address.getIpv4() + " sulla porta: " + this.address.getPort());
    }

    public ClientSocketServer accept() {
        try {
            return new ClientSocketServer(this.serverSocket.accept(), this.messageSettings);
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
