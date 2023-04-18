package server;

import client.ClientSocket;
import socket.InputChannel;
import socket.OutputChannel;
import socket.Socket;
import structures.Address;
import structures.Message;
import structures.MessageSettings;

import java.io.IOException;
import java.net.ServerSocket;

public class MultiConnectionServerSocket implements Socket {


    private java.net.ServerSocket serverSocket;

    private Address address;

    private InputChannel inputChannel;

    private OutputChannel outputChannel;

    private MessageSettings messageSettings;

    private boolean connected = false;


    public MultiConnectionServerSocket(int port) {
        try {
            this.serverSocket = new java.net.ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.address = new Address(this.serverSocket.getInetAddress().getHostAddress(), port);
        System.out.println("Server avviato all'indirizzo: " + this.address.getIpv4() + " sulla porta: " + this.address.getPort());

    }

    public ClientSocket accept() {
        try {
            return new ClientSocket(this.serverSocket.accept(), this.messageSettings);
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
