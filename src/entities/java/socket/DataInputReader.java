package socket;

import exceptions.MessageHeaderLengthException;
import structures.Message;
import structures.MessageTypes;

import java.io.IOException;
import java.util.Arrays;

public class DataInputReader {

    private final InputChannel inputChannel;

    public DataInputReader(InputChannel inputChannel) {
        this.inputChannel = inputChannel;
    }

    private ByteList readAll(int times) {
        ByteList byteList = new ByteList();
        boolean endFound = false;
        for (int i = 0; i < times; i++) {
            try {
                byte c = (byte) this.inputChannel.getSocketInputStream().read();
                if (Arrays.equals(new byte[]{byteList.getLastByte(), c}, MessageTypes.LINE_SEPARATOR.getBytes())) {
                    byteList.removeLastByte();
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
    public ByteList readAll() {
        return readAll(this.inputChannel.getClientSocket().getMessageSettings().getHeaderSize());
    }

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
        MessageTypes messageType = this.getMessageType();
        return createMessageFromByteList(this.getRawMessageData(), messageType);
    }

    private void clear() {
        this.byteList.clear();
    }
}
