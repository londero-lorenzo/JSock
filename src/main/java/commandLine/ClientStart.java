package commandLine;

import client.Client;
import structures.*;

import java.io.File;

public class ClientStart {

    public static void main(String[] args) {
        Setting<Integer> headerSetting = new Setting<>(SettingTypes.HEADER_SETTING, 3);

        new File("./Logs").mkdirs();
        File logFile = new File("./Logs/CLIENT_logFile.txt");
        Logger logger = new Logger(Logger.Type.CONSOLE, logFile);

        Setting<Logger> loggerSetting = new Setting<>(SettingTypes.LOGGER_SETTING, logger);

        Settings settings = new Settings(loggerSetting);
        MessageSettings messageSettings = new MessageSettings(headerSetting);

        SettingsCollector settingsCollector = new SettingsCollector();
        settingsCollector.add(settings);
        settingsCollector.add(messageSettings);

        Client client = new Client(settingsCollector);
        Address serverAddress = new Address("192.168.178.134", 10951);
        client.connectTo(serverAddress);
//        Message message = new Message("Hello World", MessageTypes.TX_MESSAGE);
//        client.send(message);


        Message message = new Message("Lodap", MessageTypes.SET_NAME);
        client.send(message);
    }

}
