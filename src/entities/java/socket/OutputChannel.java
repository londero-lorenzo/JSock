package socket;

import java.io.IOException;
import java.io.InputStream;

public class OutputChannel {

    private InputStream socketInputStream;


    public OutputChannel(java.net.Socket socket)
    {
        try {
            this.socketInputStream = socket.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
