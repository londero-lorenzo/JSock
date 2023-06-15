package structures;

import exceptions.SettingKeyNotFoundError;

public class Settings {

    private final SettingList settingList;

    public Settings(Setting<?>... settings) {
        this.settingList = new SettingList();
        this.settingList.add(settings);
    }

    public void add(Setting<?> setting) {
        this.settingList.add(setting);
    }

    public void changeSettingsByString(String settings) {
        this.getSettingList().changeSettingsByString(settings);
    }

    public List<Setting<?>> getChangedSettings(Settings oldSettings) {
        List<Setting<?>> changedSettings = new SettingList();
        for (Setting<?> currentSetting : this.settingList.elements)
            for (Setting<?> oldSetting : oldSettings.settingList.elements)
                if (currentSetting.getValue() != oldSetting.getValue())
                    changedSettings.add(currentSetting);
        return changedSettings;
    }


    public SettingList getSettingList() {
        return settingList;
    }

    // LOGGER SETTING

    public Logger getLogger() {
        try {
            return (Logger) this.getSettingList().getSetting(SettingTypes.LOGGER_SETTING).getValue();
        } catch (SettingKeyNotFoundError settingKeyNotFoundError) {
            Logger logger = new Logger(Logger.Type.CONSOLE);
            Setting<Logger> loggerSetting = new Setting<>(SettingTypes.LOGGER_SETTING, logger);
            this.getSettingList().add(loggerSetting);
            return logger;
        }
    }

}
