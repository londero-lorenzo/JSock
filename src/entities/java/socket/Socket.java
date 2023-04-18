package socket;

import structures.Message;
import structures.MessageSettings;

public interface Socket {

    public void setMessageSettings(MessageSettings messageSettings);

    public void send(Message message);

    public java.net.Socket getSocketObject();

    public boolean isConnected();
}
