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

    public boolean checkHeaderLengthOfMessage(Message message) {
        return (this.getHeaderLength() > message.getDataLength());
    }


    public String getDataMessageLength(Message message) {
        int messageDataLengthSize = message.getDataLengthSize();
        return (" ".repeat(Math.max(0, this.getHeaderLengthSize() - messageDataLengthSize))) + messageDataLengthSize;
    }


    public String getTypeMessageLength(Message message) {
        int messageTypeLengthSize = message.getTypeLengthSize();
        return (" ".repeat(Math.max(0, this.getHeaderLengthSize() - messageTypeLengthSize))) + messageTypeLengthSize;
    }


    public int getHeaderLength() {
        String headerString = "";
        for (int i = 0; i < (int) this.getSettingList().getSetting(SettingTypes.HEADER_SETTING).getValue(); i++)
            headerString += "9";
        return Integer.parseInt(headerString);
    }


    public int getHeaderLengthSize() {
        return (int) this.getSettingList().getSetting(SettingTypes.HEADER_SETTING).getValue();
    }

    public boolean HeaderLengthHasChanged(MessageSettings oldMessageSettings) {
        if (oldMessageSettings == null) return true;
        return this.getHeaderLengthSize() != oldMessageSettings.getHeaderLengthSize();
    }
}
