package server;

import exceptions.ExceptionHandler;
import exceptions.ServerIsAlreadyRunningException;
import structures.Address;
import structures.SettingsCollector;

public class MultiConnectionServer {

    public static MultiConnectionServer MULTI_CONNECTION_SERVER;

    private int port;

    private final MultiConnectionServerSocket multiConnectionServerSocket;

    private SettingsCollector settingsCollector;

    private ClientManagerList clientManagerList;

    private void onlyOneServerCheck() {
        if (MULTI_CONNECTION_SERVER == null)
            MULTI_CONNECTION_SERVER = this;
        else
            throw new ServerIsAlreadyRunningException(this.port);
    }

    public MultiConnectionServer(int port, SettingsCollector settingsCollector) {
        this.onlyOneServerCheck();
        this.settingsCollector = settingsCollector;
        this.port = port;
        this.multiConnectionServerSocket = new MultiConnectionServerSocket(this.port, this.settingsCollector);
        this.initializeClientManagerList();
    }

    public MultiConnectionServer(Address address, SettingsCollector settingsCollector) {
        this.onlyOneServerCheck();
        this.settingsCollector = settingsCollector;
        this.multiConnectionServerSocket = new MultiConnectionServerSocket(address, this.settingsCollector);
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
