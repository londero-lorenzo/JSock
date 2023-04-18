package server;

import client.ClientSocket;
import structures.MessageSettings;

import java.net.ServerSocket;

public class MultiConnectionServer {

    private int port;

    private MultiConnectionServerSocket multiConnectionServerSocket;


    private MessageSettings defaultMessageSettings;

    public MultiConnectionServer(MessageSettings messageSettings, int port) {
        //servirebbe una ricezione di default (messageSettings)
        this.defaultMessageSettings = messageSettings;
        this.port = port;
        this.multiConnectionServerSocket = new MultiConnectionServerSocket(this.port);
    }

    public void accept()
    {
        ClientSocket clientSocket = this.multiConnectionServerSocket.accept();
        clientSocket.read();
    }

}
