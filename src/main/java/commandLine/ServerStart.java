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
        Setting<Integer> headerSetting = new Setting<>(SettingTypes.HEADER_SETTING, 3);
        new File("./Logs").mkdirs();
        File logFile = new File("./Logs/SERVER_logFile.txt");
        Logger logger = new Logger(Logger.Type.CONSOLE, logFile);

        Setting<Logger> loggerSetting = new Setting<>(SettingTypes.LOGGER_SETTING, logger);

        Settings settings = new Settings(loggerSetting);
        MessageSettings messageSettings = new MessageSettings(headerSetting);
        Address serverAddress = new Address("192.168.178.134", 30080);
        MultiConnectionServer multiConnectionServer = new MultiConnectionServer(serverAddress, messageSettings);
        multiConnectionServer.accept();
    }

}
