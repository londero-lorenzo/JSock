package exceptions;

import structures.SettingTypes;

public class SettingKeyNotFoundError extends SocketException{
    public SettingKeyNotFoundError(SettingTypes settingKey) {
        super("No configuration setting found for key '" + settingKey + "'.");
        this.setExceptionType(ExceptionTypes.SettingKeyNotFoundException);
    }
}
