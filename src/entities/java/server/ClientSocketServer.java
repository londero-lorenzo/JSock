package server;

import client.ClientSocket;
import structures.MessageSettings;

import java.net.Socket;

public class ClientSocketServer extends ClientSocket {

    public ClientSocketServer(Socket socket, SettingsCollector settingsCollector) {
        super(socket, settingsCollector);
    }


    public void readInitialSettings() {
        String allSettings = this.inputChannel.readAll();
        this.messageSettings.changeSettingsByString(allSettings);
        System.out.println(allSettings);
    }

}
