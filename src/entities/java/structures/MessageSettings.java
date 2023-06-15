package structures;

public class MessageSettings extends Settings {

    /*
     -------------------Definizioni delle impostazioni-------------------
     (vengono caricate le impostazioni utilizzate nei pacchetti)
     */
    //   public static String HEADER_SETTING = MessageTypes.SET_HEADER_LENGTH.toString();

    public MessageSettings(Setting<?>... settings) {
        super(settings);
    }


    // TODO: controlli vari
    //private void checkHeaderLength() {
    //    if (this.maxHeaderLength < Integer.toString(this.header).length())
    //        throw new MessageHeaderLengthException(this);
    //}

    public String getDataMessageSize(Message message) {
        int messageLength = message.getDataLength();
        String messageStringSize = Integer.toString(messageLength);
        int rawMessageSize = messageStringSize.length();
        return " ".repeat(Math.max(0, (Integer) this.getSettingList().getSetting(SettingTypes.HEADER_SETTING).getValue() - rawMessageSize)) +
                messageStringSize;
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
