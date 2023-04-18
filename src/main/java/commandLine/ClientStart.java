package commandLine;

import client.Client;
import server.MultiConnectionServer;
import structures.Address;
import structures.MessageSettings;

public class ClientStart {

    public static void main(String[] args)
    {
        MessageSettings messageSettings = new MessageSettings(3, 2);
        Client client = new Client(messageSettings);
        Address serverAddress = new Address("192.168.178.134", 30080);
        client.connectTo(serverAddress);
        client.send();
    }

}
