package socket;

import exceptions.MessageHeaderLengthException;
import structures.Message;
import structures.MessageType;

import java.io.IOException;
import java.util.Arrays;

public class DataInputReader {

    private final InputChannel inputChannel;

    public DataInputReader(InputChannel inputChannel) {
        this.inputChannel = inputChannel;
    }

    /**
     * <h3>Low level method to read inbound bytes</h3> <br>
     * This method is used to read all the initial inbound settings as string. <br>
     * The maximum length of the string is represented by {@link structures.SettingTypes#HEADER_SETTING HEADER_SETTING}. <br>
     * This method keep storing all incoming bytes until {@link MessageType#LINE_SEPARATOR LINE_SEPARATOR} was found.
     * This method is more expensive than {@link #read(int)} because for each byte received, the existence of a separator is verified.
     *
     * @return {@link ByteList} containing the chars included in the initial inbound settings
     */
    public ByteList readAll() {
        int times = this.inputChannel.getClientSocket().getMessageSettings().getHeaderLength();
        ByteList byteList = new ByteList();
        boolean endFound = false;
        for (int i = 0; i < times; i++) {
            try {
                byte c = (byte) this.inputChannel.getSocketInputStream().read();
                if (Arrays.equals(new byte[]{byteList.getLastByte(), c}, MessageType.LINE_SEPARATOR.getBytes())) {
                    byteList.removeLastByte(); //to remove special char [13] used to represent LINE_SEPARATOR
                    endFound = true;
                    break;
                }
                byteList.add(c);

            } catch (IOException e) {
                if (!(e instanceof java.net.SocketException && byteList.getLength() <= times))
                    // TODO: return null -> you can handle the exception
                    throw new RuntimeException(e);
            }
        }
        if (!endFound)
            // TODO: return null -> you can handle the exception
            throw new MessageHeaderLengthException(this.inputChannel.getClientSocket().getMessageSettings());
        return byteList;
    }

    /**
     * <h3>Low level method to read inbound bytes</h3> <br>
     * This method is used to read a specified amount of incoming bytes.
     *
     * @return {@link ByteList} containing the chars included in the inbound message
     */
    private ByteList read(int times) {
        ByteList byteList = new ByteList();
        for (int i = 0; i < times; i++) {
            try {
                byte c = (byte) this.inputChannel.getSocketInputStream().read();
                byteList.add(c);
            } catch (IOException e) {
                if (!(e instanceof java.net.SocketException && byteList.getLength() <= times)) // if an exception occurs, a byte was lost
                    // TODO:
                    //  add LowIntegrityMessageError
                    //  check integrity of message
                    throw new RuntimeException(e);
            }
        }
        // TODO: return null if message is corrupted (length comparison)
        return byteList;
    }

    /**
     * <h3>Message reader method</h3>
     * This method reads all incoming bytes and creates the corresponding {@link Message} object. <br>
     * <p>
     * First of all, the type of message is read and then the actual data.
     *
     * @return incoming {@link Message}
     * @see #getMessageType()
     * @see #getRawMessageData()
     */
    public Message read() {
        MessageType messageType = this.getMessageType();
        String messageData = this.getRawMessageData().getStringFromByteList();
        return new Message(messageData, messageType);
    }

    /**
     * Method used to get the length of incoming message.
     *
     * @return the number of bytes that make up the actual data
     */
    private int getMessageLength() {
        return this.read(this.inputChannel.getClientSocket().getMessageSettings().getHeaderLengthSize()).getIntFromByteList();
    }

    /**
     * This method is used to get the {@link MessageType} of incoming {@link Message}.
     *
     * @return the corresponding enum type
     * @see MessageType
     * @see Message
     */
    private MessageType getMessageType() {
        ByteList rawMessageType = this.read(this.getMessageLength());
        return MessageType.fromString(rawMessageType.getStringFromByteList());
    }

    /**
     * This method is used to get the actual {@link ByteList} data of incoming {@link Message}. <br>
     * This method is deliberately left in a raw state as it may be useful to use the {@link ByteList} object
     *
     * @return the corresponding {@link ByteList}
     * @see ByteList
     * @see Message
     */
    private ByteList getRawMessageData() {
        return this.read(this.getMessageLength());
    }
}
