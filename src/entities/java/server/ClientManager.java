package server;


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

    }
}
