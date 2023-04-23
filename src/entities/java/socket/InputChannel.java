package socket;

import client.ClientSocket;

import java.io.IOException;
import java.io.InputStream;

public class InputChannel {

    private InputStream socketInputStream;

    private DataInputReader dataInputReader;

    private ClientSocket socket;


    public InputChannel(ClientSocket socket) {
        this.socket = socket;
        try {
            this.socketInputStream = this.socket.getSocketObject().getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.dataInputReader = new DataInputReader(this);
    }

    public String read() {
        ByteList byteList = this.dataInputReader.readAll();
        return new String(byteList.getBytes());
        /*
        try {
            data = this.socketInputStream.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
*/
        // System.out.println(data);
    }

    public String readAll()
    {
        ByteList byteList = this.dataInputReader.readAll();
        return new String(byteList.getBytes());
    }

    public InputStream getSocketInputStream() {
        return this.socketInputStream;
    }

    public ClientSocket getClientSocket() {
        return socket;
    }
}
