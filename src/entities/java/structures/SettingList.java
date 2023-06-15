package structures;

import exceptions.SettingKeyNotFoundError;

import java.util.Objects;

public class SettingList extends List<Setting<?>> {

    public void add(Setting<?>... messageSettings) {
        for (Setting<?> messageSetting : messageSettings) {
            this.add(messageSetting);
        }
    }

    public Setting<?> getSetting(SettingTypes settingType) {
        for (Setting<?> messageSetting : this.elements) {
            if (Objects.equals(messageSetting.getType(), settingType)) {
                return messageSetting;
            }
        }
        throw new SettingKeyNotFoundError(settingType);
    }

    public void changeSettingsByString(String allSetting) {
        for (Setting<?> messageSetting : this.elements) {
            String allSettingSub = allSetting.substring(allSetting.indexOf(messageSetting.getName()) + messageSetting.getName().length());
            messageSetting.setValueFromString((allSettingSub.substring(0, (allSettingSub.indexOf('!') != -1) ? allSettingSub.indexOf('!') : allSettingSub.length())));
        }
    }
}
