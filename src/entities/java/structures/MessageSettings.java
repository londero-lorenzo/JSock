package structures;

public class MessageSettings extends Settings {

    public static String HEADER_SETTING = MessageTypes.SET_HEADER_LENGTH;


    private SettingList settingList;

    public MessageSettings(Setting<?>... settings) {
        this.settingList = new SettingList();
        this.settingList.add(settings);
    }

    public void add(Setting<?> messageSetting) {
        this.settingList.add(messageSetting);
    }


    // TODO: controlli vari
    //private void checkHeaderLength() {
    //    if (this.maxHeaderLength < Integer.toString(this.header).length())
    //        throw new MessageHeaderLengthException(this);
    //}


    public void changeSettingsByString(String settings) {
        this.settingList.changeSettingsByString(settings);
    }

    public int getHeaderSize() {
        String headerString = "";
        for (int i = 0; i < (int) this.settingList.getSetting(HEADER_SETTING).getValue(); i++)
            headerString += "9";
        return Integer.parseInt(headerString);
    }

    public int getHeaderLength() {
        return (int) this.settingList.getSetting(HEADER_SETTING).getValue();
    }
}
