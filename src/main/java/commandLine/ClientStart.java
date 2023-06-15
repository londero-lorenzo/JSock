package commandLine;

import client.Client;
import structures.*;

import java.io.File;

public class ClientStart {

    public static void main(String[] args) {
        Setting<Integer> headerSetting = new Setting<>(SettingTypes.HEADER_SETTING, 3);
        MessageSettings messageSettings = new MessageSettings(headerSetting);
        Client client = new Client(messageSettings);
        Address serverAddress = new Address("192.168.178.134", 30080);
        client.connectTo(serverAddress);
    }

}
