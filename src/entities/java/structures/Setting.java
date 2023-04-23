package structures;

import exceptions.UnalterableSettingByStringException;

public class Setting<TypeOfValue> {
    private final String name;

    private TypeOfValue value;

    public Setting(String settingName, TypeOfValue value) {
        this.name = settingName;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public TypeOfValue getValue() {
        return this.value;
    }

    public void setValue(TypeOfValue newValue) {
        this.value = newValue;
    }

    public void setValueFromString(String newValue) {
        if (this.value.getClass() == Integer.class) {
            this.setValue((TypeOfValue) Integer.valueOf(newValue));
        } else if (this.value.getClass() == String.class) {
            this.setValue((TypeOfValue) newValue);
        } else if (this.value.getClass() == Byte.class) {
            this.setValue((TypeOfValue) Byte.valueOf(newValue));
        } else {
            throw new UnalterableSettingByStringException(this);
        }

    }
}
