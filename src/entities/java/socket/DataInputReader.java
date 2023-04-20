package socket;

import java.io.IOException;

public class DataInputReader {

    private InputChannel inputChannel;

    private ByteList byteList;

    public DataInputReader(InputChannel inputChannel) {
        this.inputChannel = inputChannel;
        this.byteList = new ByteList();
    }

    public ByteList read() {

        for (int i = 0; i < this.inputChannel.getClientSocket().getMessageSettings().getHeaderSize(); i++) {
            try {
                this.byteList.add((byte) this.inputChannel.getSocketInputStream().read());
            } catch (IOException e) {
                if (e instanceof java.net.SocketException && this.byteList.getLength() < this.inputChannel.getClientSocket().getMessageSettings().getHeaderSize())
                    return this.byteList;
                else
                    throw new RuntimeException(e);
            }
        }
        return this.byteList;

    }
}
