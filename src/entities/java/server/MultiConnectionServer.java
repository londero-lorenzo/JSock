package server;

import client.ClientSocket;
import structures.Address;
import structures.MessageSettings;

public class MultiConnectionServer {

    private int port;

    private MultiConnectionServerSocket multiConnectionServerSocket;


    private MessageSettings defaultMessageSettings;

    public MultiConnectionServer(int port, MessageSettings messageSettings) {
        //servirebbe una ricezione di default (messageSettings)
        this.defaultMessageSettings = messageSettings;
        this.port = port;
        this.multiConnectionServerSocket = new MultiConnectionServerSocket(this.port, this.defaultMessageSettings);
    }

    public MultiConnectionServer(Address address, MessageSettings messageSettings) {
        this.defaultMessageSettings = messageSettings;
        this.multiConnectionServerSocket = new MultiConnectionServerSocket(address, this.defaultMessageSettings);
    }

    public void accept() {
        ClientSocket clientSocket = this.multiConnectionServerSocket.accept();
        clientSocket.read();
    }

}
