package commandLine;

import server.MultiConnectionServer;
import structures.Address;
import structures.MessageSettings;
import structures.Setting;
import structures.SettingTypes;
import structures.Logger;
import structures.Settings;
import structures.SettingsCollector;

import java.io.File;

public class ServerStart {

    public static void main(String[] args) {
        Setting<Integer> headerSetting = new Setting<>(MessageSettings.HEADER_SETTING, 3);
        MessageSettings messageSettings = new MessageSettings(headerSetting);
        Address serverAddress = new Address("192.168.178.134", 30080);
        MultiConnectionServer multiConnectionServer = new MultiConnectionServer(serverAddress, messageSettings);
        multiConnectionServer.accept();
    }

}
