package exceptions;

import structures.Setting;

public class UnalterableSettingByStringException extends SocketException {
    public UnalterableSettingByStringException(Setting<?> setting) {
        super("String type cannot be casted into '" + setting.getValue().getClass().getSimpleName() + "'");
        this.setExceptionType(ExceptionTypes.UnalterableSettingByStringException);
    }
}
