package exceptions;

import structures.SettingTypes;

public class SettingKeyNotFoundError extends RuntimeException {
    public SettingKeyNotFoundError(SettingTypes settingKey) {
        super("No configuration setting found for key '" + settingKey + "'");
    }
}
