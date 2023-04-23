package server;

import client.ClientSocket;
import structures.MessageSettings;

import java.net.Socket;

public class ClientSocketServer extends ClientSocket {

    public ClientSocketServer(Socket socket, MessageSettings messageSettings) {
        super(socket, messageSettings);
    }


    public void readInitialSettings() {
        String allSettings = this.inputChannel.readAll();
        this.messageSettings.changeSettingsByString(allSettings);
        System.out.println(allSettings);
    }

}
