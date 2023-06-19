package server;


import structures.Message;
import structures.MessageTypes;

public class ClientManager implements Runnable {
    private final ClientSocketServer clientSocket;

    public ClientManager(ClientSocketServer clientSocket) {
        this.clientSocket = clientSocket;
        this.readInitialSettings();
    }

    private void readInitialSettings() {
        this.clientSocket.readInitialSettings();
    }


    @Override
    public void run() {
        while (true) {
            Message inboundMessage = this.clientSocket.read();
            if (inboundMessage.getType() == MessageTypes.RX_NORMAL_MESSAGE) {

            }
        }
    }
}
