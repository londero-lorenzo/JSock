package structures;

import exceptions.UnalterableSettingByStringException;

public class Setting<TypeOfValue> {
    private final SettingTypes type;

    private TypeOfValue value;

    public Setting(SettingTypes settingType, TypeOfValue value) {
        this.type = settingType;
        this.value = value;
    }

    public String getName() {
        return this.type.toString();
    }

    public SettingTypes getType() {
        return this.type;
    }

    public TypeOfValue getValue() {
        return this.value;
    }

    public void setValue(TypeOfValue newValue) {
        this.value = newValue;
    }

    public void setValueFromString(String newValue) {
        if (this.value.getClass() == Integer.class) {
            @SuppressWarnings("unchecked") TypeOfValue newSettingValue = (TypeOfValue) Integer.valueOf(newValue);
            this.setValue(newSettingValue);
        } else if (this.value.getClass() == String.class) {
            @SuppressWarnings("unchecked") TypeOfValue newSettingValue = (TypeOfValue) String.valueOf(newValue);
            this.setValue(newSettingValue);
        } else if (this.value.getClass() == Byte.class) {
            @SuppressWarnings("unchecked") TypeOfValue newSettingValue = (TypeOfValue) Byte.valueOf(newValue);
            this.setValue(newSettingValue);
        } else {
            throw new UnalterableSettingByStringException(this);
        }

    }
}
