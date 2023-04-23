package server;

import client.ClientSocket;
import exceptions.ServerIsAlreadyRunningException;
import structures.Address;
import structures.MessageSettings;

public class MultiConnectionServer {

    public static MultiConnectionServer MULTI_CONNECTION_SERVER;

    private int port;

    private MultiConnectionServerSocket multiConnectionServerSocket;

    private MessageSettings defaultMessageSettings;

    private ClientManagerList clientManagerList;

    private void onlyOneServerCheck() {
        if (MULTI_CONNECTION_SERVER == null)
            MULTI_CONNECTION_SERVER = this;
        else
            throw new ServerIsAlreadyRunningException(this.port);
    }

    public MultiConnectionServer(int port, MessageSettings messageSettings) {
        this.onlyOneServerCheck();
        this.defaultMessageSettings = messageSettings;
        this.port = port;
        this.multiConnectionServerSocket = new MultiConnectionServerSocket(this.port, this.defaultMessageSettings);
        this.initializeClientManagerList();
    }

    public MultiConnectionServer(Address address, MessageSettings messageSettings) {
        this.onlyOneServerCheck();
        this.defaultMessageSettings = messageSettings;
        this.multiConnectionServerSocket = new MultiConnectionServerSocket(address, this.defaultMessageSettings);
        this.initializeClientManagerList();
    }

    private void initializeClientManagerList() {
        this.clientManagerList = new ClientManagerList();
    }

    public void accept() {
        while (true) {
            ClientSocketServer clientSocket = this.multiConnectionServerSocket.accept();
            ClientManager clientManager = new ClientManager(clientSocket);
            Thread runClientManage = new Thread(clientManager);
            runClientManage.start();
            this.clientManagerList.add(clientManager);
        }
    }

}
