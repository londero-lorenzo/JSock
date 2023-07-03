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

    /**
     * This method is used to get the byte length of the data part of a message.
     * <br>
     * The returned length is represented as a string, because during transmission a number of bytes equal to the number of digits indicated by the {@link SettingTypes#HEADER_SETTING} must be sent.<br>
     * For this reason, it is implemented a padding string which has the same length of the number byte left to reach the {@link SettingTypes#HEADER_SETTING} length.
     *
     * @param message the {@link Message} object in which is the data to send
     * @return the length of data message
     */
    public String getDataMessageLength(Message message) {
        int messageDataLengthSize = message.getDataLengthSize();
        return (" ".repeat(Math.max(0, this.getHeaderLengthSize() - messageDataLengthSize))) + messageDataLengthSize;
    }

    public String getTypeMessageLength(Message message) {
        int messageTypeLengthSize = message.getDataLengthSize();
        return (" ".repeat(Math.max(0, this.getHeaderLengthSize() - messageTypeLengthSize))) + messageTypeLengthSize;
    }

    /**
     * This method is used to get the maximum number of a message, the number is generated using the number of digits indicated by {@link SettingTypes#HEADER_SETTING HEADER_SETTING}.
     *
     * @return the maximum number of bytes for a message
     * @see SettingTypes#HEADER_SETTING
     */
    public int getHeaderLength() {
        String headerString = "";
        for (int i = 0; i < (int) this.getSettingList().getSetting(SettingTypes.HEADER_SETTING).getValue(); i++)
            headerString += "9";
        return Integer.parseInt(headerString);
    }

    /**
     * This method is used to get the amount of digits that indicate the maximum number of bytes that can be received.
     *
     * @return the value of {@link SettingTypes#HEADER_SETTING HEADER_SETTING} as int
     * @see SettingTypes#HEADER_SETTING
     */
    public int getHeaderLengthSize() {
        return (int) this.getSettingList().getSetting(SettingTypes.HEADER_SETTING).getValue();
    }

    public boolean HeaderLengthHasChanged(MessageSettings oldMessageSettings) {
        if (oldMessageSettings == null) return true;
        return this.getHeaderLengthSize() != oldMessageSettings.getHeaderLengthSize();
    }
}
