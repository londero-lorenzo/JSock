package commandLine;

import client.Client;
import structures.Address;
import structures.Message;
import structures.MessageTypes;
import structures.MessageSettings;

public class ClientStart {

    public static void main(String[] args) {
        MessageSettings messageSettings = new MessageSettings(3);
        Client client = new Client(messageSettings);
        Address serverAddress = new Address("192.168.178.134", 30080);
        client.connectTo(serverAddress);
        Message message = new Message("Hello World", MessageTypes.TX_MESSAGE);
        client.send(message);
    }

}
