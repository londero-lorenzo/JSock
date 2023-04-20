package commandLine;

import server.MultiConnectionServer;
import structures.Address;
import structures.MessageSettings;

public class ServerStart {

    public static void main(String[] args)
    {
        MessageSettings messageSettings = new MessageSettings(3);
        Address serverAddress = new Address("192.168.178.134", 30080);
        MultiConnectionServer multiConnectionServer = new MultiConnectionServer(serverAddress, messageSettings);
        multiConnectionServer.accept();
    }

}
