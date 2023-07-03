package structures;

public enum SettingTypes {
    /**
     * Setting that refers to the maximum length of a message. <br>
     * The value of this setting must be an integer, representing the length of a string within which the maximum number expressed in base 10 is represented.
     * <br>
     * e.g.
     * <pre>HEADER_SETTING = 3 -> maximumLength = 999</pre>
     */
    HEADER_SETTING {
        @Override
        public String toString() {
            return MessageType.SET_HEADER_LENGTH.toString();
        }
    },
    LOGGER_SETTING {
        @Override
        public String toString() {
            return "!LOGGER_SETTING";
        }
    },
}
