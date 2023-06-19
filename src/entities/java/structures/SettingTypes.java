package structures;

public enum SettingTypes {

    HEADER_SETTING {
        @Override
        public String toString() {
            return MessageTypes.SET_HEADER_LENGTH.toString();
        }
    },
    LOGGER_SETTING {
        @Override
        public String toString() {
            return "!LOGGER_SETTING";
        }
    },
}