package server;


import client.ClientSocket;
import structures.Message;
import structures.MessageType;

public class ClientManager implements Runnable {
    private final ClientSocket clientSocket;


    public ClientManager(ClientSocket clientSocket) {
        this.clientSocket = clientSocket;
    }


    @Override
    public void run() {
        while (true) {
            Message inboundMessage = this.clientSocket.read();
            if (inboundMessage.getType() == MessageType.RX_NORMAL_MESSAGE) {

            }
        }
    }
}
