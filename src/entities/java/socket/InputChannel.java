package socket;

import client.ClientSocket;
import structures.Message;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class InputChannel {

    private final InputStream socketInputStream;

    private final DataInputReader dataInputReader;

    private final ClientSocket socket;


    public InputChannel(ClientSocket socket) {
        this.socket = socket;
        try {
            this.socketInputStream = this.socket.getSocketObject().getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.dataInputReader = new DataInputReader(this);
    }


    public String readAll() {
        this.socket.getLogger().logWithTime("> Receiving Initial Settings... <\n");
        ByteList byteList = this.dataInputReader.readAll();
        this.socket.getLogger().logWithPadding("Raw settings data length: " + byteList.getBytes().length);
        this.socket.getLogger().logWithPadding("Raw settings data: " + Arrays.toString(byteList.getBytes()));
        this.socket.getLogger().logWithPadding("Settings data received: " + new String(byteList.getBytes()));
        this.socket.getLogger().logWithPaddingAndBR("> End Initial Settings Reception <\n");
        return new String(byteList.getBytes());
    }

    public Message read() {
        this.socket.getLogger().logWithTime("> Receiving... <\n");
        Message receivedMessage = this.dataInputReader.read();
        this.socket.getLogger().logWithPadding("Header length: " + this.socket.getMessageSettings().getHeaderLength());
        this.socket.getLogger().logWithPadding("Message data type: " + receivedMessage.getType());
        this.socket.getLogger().logWithPadding("Raw Message data: " + Arrays.toString(receivedMessage.getBytes()));
        this.socket.getLogger().logWithPadding("Message data: " + receivedMessage);
        this.socket.getLogger().logWithPadding("Total bytes of message received: " + (receivedMessage.getType().length() + receivedMessage.getBytes().length));
        this.socket.getLogger().logWithPaddingAndBR("> End Reception <");
        return receivedMessage;
        /*
        try {
            data = this.socketInputStream.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
*/
        // System.out.println(data);
    }


    public InputStream getSocketInputStream() {
        return this.socketInputStream;
    }

    public ClientSocket getClientSocket() {
        return socket;
    }
}
