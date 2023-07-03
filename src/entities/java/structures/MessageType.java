package structures;

import java.util.Objects;


public enum MessageType {

    SET_HEADER_LENGTH {
        @Override
        public String toString() {
            return "!SET_HEADER_LENGTH";
        }
    },

    SET_NAME {
        @Override
        public String toString() {
            return "!SET_NAME";
        }
    },

    TX_MESSAGE {
        @Override
        public String toString() {
            return "!MESSAGE";
        }
    },

    RX_NORMAL_MESSAGE {
        @Override
        public String toString() {
            return "!N_MSG";
        }
    },

    RX_LONG_MESSAGE {
        @Override
        public String toString() {
            return "!L_MSG";
        }
    },

    RX_END_LONG_MESSAGE {
        @Override
        public String toString() {
            return "!F_L_MSG";
        }
    },

    RECV_MSG {
        @Override
        public String toString() {
            return "!RECIVED";
        }
    },

    END_SETTINGS_SEPARATOR {
        @Override
        public String toString() {
            return "!END_SETTINGS_SEPARATOR";
        }
    };

    public static MessageType fromString(String text) {
        for (MessageType messageType : MessageType.values())
            if (Objects.equals(messageType.toString(), text))
                return messageType;
        return null;
    }

    // utilizzato solo per quando vengono inviate le impostazioni del socket client
    public static final String LINE_SEPARATOR = "\r\n";

    public int length() {
        return this.toString().length();
    }

    public byte[] getBytes() {
        return this.toString().getBytes();
    }
}
