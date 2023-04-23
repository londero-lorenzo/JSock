package exceptions;

import structures.MessageSettings;

public class SettingKeyNotFoundError extends RuntimeException {
    public SettingKeyNotFoundError(String settingKey) {
        super("No configuration setting found for key '" + settingKey + "'");
    }
}
