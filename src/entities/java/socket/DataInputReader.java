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
                    throw new RuntimeException(e);
            }
        }
        if (!endFound)
            throw new MessageHeaderLengthException(this.inputChannel.getClientSocket().getMessageSettings());
        return byteList;
    }


    /**
     * Questo metodo leggerà ogni singolo byte in ricezione dal server a cui è connesso.
     * <p>
     * La lista di caratteri ricevuti conterranno tutte le informazioni per stabilire una connessione
     *
     * @return
     */
    private ByteList read(int times) {
        ByteList byteList = new ByteList();
        for (int i = 0; i < times; i++) {
            try {
                byte c = (byte) this.inputChannel.getSocketInputStream().read();
                byteList.add(c);
            } catch (IOException e) {
                if (!(e instanceof java.net.SocketException && byteList.getLength() <= times))
                    throw new RuntimeException(e);
            }
        }
        return byteList;
    }


    public Message read() {
        MessageType messageType = this.getMessageType();
        String messageData = this.getRawMessageData().getStringFromByteList();
        return new Message(messageData, messageType);
    }

    private int getMessageLength() {
        return this.read(this.inputChannel.getClientSocket().getMessageSettings().getHeaderLengthSize()).getIntFromByteList();
    }


    private MessageType getMessageType() {
        ByteList rawMessageType = this.read(this.getMessageLength());
        return MessageType.fromString(rawMessageType.getStringFromByteList());
    }


    private ByteList getRawMessageData() {
        return this.read(this.getMessageLength());
    }
}
