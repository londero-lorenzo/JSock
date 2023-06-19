package server;

import client.ClientSocket;
import exceptions.ExceptionHandler;
import structures.MessageSettings;
import structures.SettingsCollector;
import structures.List;
import structures.Setting;

import java.net.Socket;

public class ClientSocketServer extends ClientSocket {

    public ClientSocketServer(Socket socket, SettingsCollector settingsCollector, ExceptionHandler exceptionHandler) {
        super(socket, settingsCollector, exceptionHandler);
    }


    public void readInitialSettings() {
        String allSettings = this.inputChannel.readAll();
        MessageSettings oldMessageSettings = this.getSettingsCollector().getMessageSettings();
        this.getSettingsCollector().getMessageSettings().changeSettingsByString(allSettings);
        List<Setting<?>> changedSettings = this.getSettingsCollector().getMessageSettings().getChangedSettings(oldMessageSettings);
        for (Setting<?> changedSetting : changedSettings.getElements()) {
            this.getLogger().logWithPadding("Impostazione modificata: " + changedSetting.getName());
            this.getLogger().logWithPadding("Nuovo valore associato: " + changedSetting.getValue());
        }
    }

}
